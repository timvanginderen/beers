package be.tim.beers

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.tim.beers.data.*
import be.tim.beers.data.remote.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BeersFragment : Fragment() {

    private val TAG = BeersFragment::class.qualifiedName

    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager


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

        apiClient = ApiClient()
        sessionManager = SessionManager(requireContext())

        // TODO: 05/11/2020  check if a stored token exists
        val authToken = sessionManager.fetchAuthToken()
        if (authToken == null) {
            login()
        } else {
            getBeers()
        }
    }

    private fun login() {
        val userInfo = UserInfo( userName = "star_developer@icapps.com", password = "developer")
        apiClient.getApiService(requireContext()).login(userInfo).enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                Log.d(TAG, "Login call failed")
            }

            override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {
                if (response?.code() == 200) {
                    val response = response.body() as LoginResponse
                    val token = response.data.accessToken
                    sessionManager.saveAuthToken(token)
                    Log.d(TAG, "Login success with token: $token")

                    getBeers()
                } else {
                    // TODO: 05/11/2020 handle error
                }
            }
        })
    }

    private fun getBeers() {
        apiClient.getApiService(requireContext()).getBeers().enqueue(object : Callback<BeersResponseWrapper> {
            override fun onFailure(call: Call<BeersResponseWrapper>?, t: Throwable?) {
                Log.d(TAG, "Get beers call failed")
            }

            override fun onResponse(call: Call<BeersResponseWrapper>?, response: Response<BeersResponseWrapper>?) {
                val response = response!!.body() as BeersResponseWrapper
                val beers = response.data

                Log.d(TAG, "Get beers success: loaded ${beers.size} beers")
            }
        })
    }
}