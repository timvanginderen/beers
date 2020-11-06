package be.tim.beers.beerdetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import be.tim.beers.R
import be.tim.beers.data.Beer
import be.tim.beers.data.ResponseWrapper
import be.tim.beers.data.local.SessionManager
import be.tim.beers.data.remote.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BeerDetailFragment : Fragment() {

    private val TAG = BeerDetailFragment::class.qualifiedName

    val args: BeerDetailFragmentArgs by navArgs()

    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager

    private lateinit var ivBeer: ImageView
    private lateinit var tvBeerName: TextView
    private lateinit var tvBreweryName: TextView
    private lateinit var tvBreweryAddress: TextView

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
                }
            }
        })
    }

}