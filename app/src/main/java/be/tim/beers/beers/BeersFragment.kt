package be.tim.beers.beers

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.tim.beers.R
import be.tim.beers.data.*
import be.tim.beers.data.local.SessionManager
import be.tim.beers.data.remote.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BeersFragment : Fragment() {

    private val TAG = BeersFragment::class.qualifiedName

    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager

    private lateinit var rvBeers : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.beers_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvBeers = view.findViewById<View>(R.id.rv_beers) as RecyclerView
        rvBeers.layoutManager = LinearLayoutManager(view.context)
    }

    override fun onResume() {
        super.onResume()

        apiClient = ApiClient()
        sessionManager = SessionManager(requireContext())

        if (sessionManager.fetchAuthToken() == null) {
            login()
        } else {
            getBeers()
        }
    }

    private fun login() {
        val userInfo = UserInfo( userName = "star_developer@icapps.com", password = "developer")

        apiClient.getApiService(requireContext()).login(userInfo).enqueue(object : Callback<ResponseWrapper<LoginData>> {
            override fun onFailure(call: Call<ResponseWrapper<LoginData>>?, t: Throwable?) {
                Log.d(TAG, "Login call failed")
            }

            override fun onResponse(call: Call<ResponseWrapper<LoginData>>?, response: Response<ResponseWrapper<LoginData>>?) {
                if (response?.code() == 200) {
                    val response = response.body() as ResponseWrapper<LoginData>
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
        apiClient.getApiService(requireContext()).getBeers().enqueue(object : Callback<ResponseWrapper<List<Beer>>> {
            override fun onFailure(call: Call<ResponseWrapper<List<Beer>>>?, t: Throwable?) {
                Log.d(TAG, "Get beers call failed")
            }

            override fun onResponse(call: Call<ResponseWrapper<List<Beer>>>?, response: Response<ResponseWrapper<List<Beer>>>?) {
                val response = response!!.body() as ResponseWrapper<List<Beer>>
                val beers = response.data

                Log.d(TAG, "Get beers success: loaded ${beers.size} beers")

                rvBeers.adapter = BeersAdapter(beers)
            }
        })
    }
}