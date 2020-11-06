package be.tim.beers.beerdetail

import android.app.Dialog
import android.content.DialogInterface
import android.media.Rating
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.navArgs
import be.tim.beers.R
import be.tim.beers.data.Beer
import be.tim.beers.data.ResponseWrapper
import be.tim.beers.data.local.SessionManager
import be.tim.beers.data.remote.ApiClient
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BeerDetailFragment : Fragment() {

    private val TAG = BeerDetailFragment::class.qualifiedName

    private val args: BeerDetailFragmentArgs by navArgs()

    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager

    private lateinit var ivBeer: ImageView
    private lateinit var tvBeerName: TextView
    private lateinit var tvBreweryName: TextView
    private lateinit var tvBreweryAddress: TextView
    private lateinit var btnRate: Button

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.d(TAG, "onActivityCreated with beerId: ${args.beerId}")

        apiClient = ApiClient()
        sessionManager = SessionManager(requireContext())

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
        btnRate = view.findViewById<View>(R.id.btn_rate) as Button

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getBeer(beerId: String) {
        apiClient.getApiService(requireContext()).getBeer(beerId).enqueue(object : Callback<ResponseWrapper<Beer>> {
            override fun onFailure(call: Call<ResponseWrapper<Beer>>?, t: Throwable?) {
                Log.d(TAG, "Get beer ($beerId) call failed")
            }

            override fun onResponse(call: Call<ResponseWrapper<Beer>>?, response: Response<ResponseWrapper<Beer>>?) {
                if (response?.code() == 200) {
                    val response = response.body() as ResponseWrapper<Beer>
                    val beer = response.data

                    Log.d(TAG, "Get beer ${beer.name} success")

                    tvBeerName.text = beer.name
                    tvBreweryName.text =  beer.brewery.name
                    tvBreweryAddress.text = beer.brewery.address

                    Picasso.get().load(beer.imageUrl).into(ivBeer)

                    btnRate.setOnClickListener {
                        showRatingDialog()
                    }
                }
            }
        })
    }

    private fun showRatingDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.rating_dialog, null)
        val ratingBar = dialogView.findViewById<View>(R.id.rating) as RatingBar
        var rating : Float = 0.0F
        ratingBar.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener {
            p0: RatingBar?, p1: Float, p2: Boolean -> rating = p1
        }

        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }

        builder?.setMessage(R.string.dialog_message)
                ?.setTitle(R.string.dialog_title)
                ?.setPositiveButton(R.string.send) { dialog, id ->
                    // TODO: 06/11/2020 send rating
                    Toast.makeText(context, "$rating stars selected", Toast.LENGTH_LONG).show()
                }
                ?.setView(dialogView)

        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }

}