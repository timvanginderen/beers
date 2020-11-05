package be.tim.beers

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import be.tim.beers.data.LoginData
import be.tim.beers.data.LoginResponse
import be.tim.beers.data.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


class BeersFragment : Fragment() {

    private val TAG = BeersFragment::class.qualifiedName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.beers_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val action = BeersFragmentDirections.actionBeersFragmentToBeersDetailFragment(220)
//        view.findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()

        // TODO: 05/11/2020  check if a stored token exists
        login()
    }

    private fun login() {
        val retrofit = Retrofit.Builder()
            .baseUrl(" https://icapps-nodejs-beers-api.herokuapp.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: BeerService = retrofit.create(BeerService::class.java)

        val userInfo = UserInfo( userName = "star_developer@icapps.com", password = "developer")
        val repo: Call<LoginResponse> = service.login(userInfo)

        repo.enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                Log.d(TAG, "Login call failed")
            }

            override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {
                val response = response!!.body() as LoginResponse
                val token = response.data.accessToken
                Log.d(TAG, "Login success with token: $token")
            }
        })
    }
}

// TODO: 05/11/2020  extract
interface BeerService {
    @Headers("Content-Type: application/json")
    @POST("auth/login")
    fun login(@Body userInfo: UserInfo) : Call<LoginResponse>
}
