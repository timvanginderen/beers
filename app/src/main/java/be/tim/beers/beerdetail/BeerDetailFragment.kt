package be.tim.beers.beerdetail

import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import be.tim.beers.R
import be.tim.beers.data.Beer
import be.tim.beers.data.RatingInfo
import be.tim.beers.data.remote.ResponseWrapper
import be.tim.beers.data.local.SessionManager
import be.tim.beers.data.remote.ApiClient
import be.tim.beers.util.Util
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class BeerDetailFragment : Fragment() {

    private val TAG = BeerDetailFragment::class.qualifiedName

    private val args: BeerDetailFragmentArgs by navArgs()

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager

    private lateinit var ivBeer: ImageView
    private lateinit var tvBeerName: TextView
    private lateinit var tvBreweryName: TextView
    private lateinit var tvBreweryAddress: TextView
    private lateinit var rbBeer: RatingBar
    private lateinit var btnRate: Button
    private lateinit var btnShowMap: Button

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.d(TAG, "onActivityCreated with beerId: ${args.beerId}")

        apiClient = ApiClient()
        sessionManager = SessionManager(requireContext())

        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_CLASS, BeerDetailFragment::class.qualifiedName.toString())
        }

        getBeer(args.beerId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // TODO: 06/11/2020 use ViewModel
        val view  = inflater.inflate(R.layout.beerdetail_frag, container, false)

        ivBeer = view.findViewById<View>(R.id.iv_beer) as ImageView
        tvBeerName = view.findViewById<View>(R.id.tv_beer_name) as TextView
        tvBreweryName = view.findViewById<View>(R.id.tv_brewery_name) as TextView
        tvBreweryAddress = view.findViewById<View>(R.id.tv_brewery_address) as TextView
        rbBeer = view.findViewById<View>(R.id.rb_beer) as RatingBar
        btnRate = view.findViewById<View>(R.id.btn_rate) as Button
        btnShowMap = view.findViewById<View>(R.id.btn_show_map) as Button

        return view
    }

    private fun getBeer(beerId: String) {
        apiClient.getApiService(requireContext()).getBeer(beerId).enqueue(object :
            Callback<ResponseWrapper<Beer>> {
            override fun onFailure(call: Call<ResponseWrapper<Beer>>?, t: Throwable?) {
                Log.d(TAG, "Get beer ($beerId) call failed")
            }

            override fun onResponse(
                    call: Call<ResponseWrapper<Beer>>?,
                    response: Response<ResponseWrapper<Beer>>?
            ) {
                if (response?.code() == 200) {
                    val res = response.body() as ResponseWrapper<Beer>
                    val beer = res.data

                    Log.d(TAG, "Get beer ${beer.name} success")

                    tvBeerName.text = beer.name
                    tvBreweryName.text = beer.brewery.name
                    val addressLine = "${beer.brewery.address}, ${beer.brewery.city} " +
                            beer.brewery.country
                    tvBreweryAddress.text = addressLine

                    if (beer.rating != null) {
                        rbBeer.visibility = View.VISIBLE
                        rbBeer.rating = beer.rating!!.toFloat()
                    } else {
                        rbBeer.visibility = View.GONE
                    }

                    Picasso.get().load(beer.imageUrl).into(ivBeer)

                    val location = getLocation(addressLine)
                    if (location != null) {
                        btnShowMap.visibility = View.VISIBLE
                        btnShowMap.setOnClickListener {
                            val action = BeerDetailFragmentDirections.actionBeersDetailFragmentToBreweryMapFragment(
                                    Util.convertLocationToLong(location.latitude),
                                    Util.convertLocationToLong(location.longitude),
                                    beer.brewery.name)
                            findNavController().navigate(action)
                        }
                    }

                    btnRate.setOnClickListener {
                        showRatingDialog()
                    }

                }
            }
        })
    }

    private fun getLocation(address: String) : Location? {
        val geoCoder = Geocoder(context, Locale.getDefault())

        return try {
            val addresses = geoCoder.getFromLocationName(address, 1)
            val location = Location("Beers")
            location.latitude = addresses[0].latitude
            location.longitude = addresses[0].longitude
            location
        } catch (e: Exception) {
            if (e.message != null) {
                Log.e(TAG, e.message!!)
            }
            null
        }
    }

    private fun showRatingDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.rating_dialog, null)
        val ratingBar = dialogView.findViewById<View>(R.id.rating) as RatingBar
        var rating = 0.0F

        ratingBar.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener {
            p0: RatingBar?, p1: Float, p2: Boolean -> rating = p1
        }

        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }

        builder?.setMessage(R.string.dialog_message)
                ?.setTitle(R.string.dialog_title)
                ?.setPositiveButton(R.string.send) { dialog, id ->
                    updateRating(args.beerId, rating.toInt())
                }
                ?.setView(dialogView)

        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }

    private fun updateRating(beerId: String, rating: Int) {
        apiClient.getApiService(requireContext()).updateRating(beerId, RatingInfo(rating = rating))
                .enqueue(object : Callback<ResponseWrapper<Beer>> {
            override fun onFailure(call: Call<ResponseWrapper<Beer>>?, t: Throwable?) {
                Log.d(TAG, "Update rating ($beerId) call failed")
            }

            override fun onResponse(call: Call<ResponseWrapper<Beer>>?,
                                    response: Response<ResponseWrapper<Beer>>?) {
                Log.d(TAG, "Update rating call success")

                if (response?.code() == 200) {
                    val res = response.body() as ResponseWrapper<Beer>
                    val beer = res.data
                    rbBeer.rating = if (beer.rating == null) 0.0F else beer.rating!!.toFloat()
                    Toast.makeText(context, "Thank you for rating ${beer.name}!", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

}