package be.tim.beers.data.remote

import be.tim.beers.data.Beer
import be.tim.beers.data.BeerDataSource
import be.tim.beers.data.OperationCallback
import be.tim.beers.data.ResponseWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BeerRemoteDataSource(apiClient: ApiClient, token:String) : BeerDataSource {

    private var call: Call<ResponseWrapper<List<Beer>>>? = null
    private val service = apiClient.build(token)

    override fun retrieveBeers(callback: OperationCallback<Beer>) {
        call = service?.getBeers()
        call?.enqueue(object : Callback<ResponseWrapper<List<Beer>>> {
            override fun onFailure(call: Call<ResponseWrapper<List<Beer>>>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(
                call: Call<ResponseWrapper<List<Beer>>>,
                response: Response<ResponseWrapper<List<Beer>>>
            ) {
                response.body()?.let {
                    if (response.isSuccessful) {
                        callback.onSuccess(it.data)
                    } else {
//                        callback.onError(it.msg)
                    }
                }
            }
        })
    }

    override fun cancel() {
        call?.let {
            it.cancel()
        }
    }
}