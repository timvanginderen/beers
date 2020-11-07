package be.tim.beers.data.remote

import android.content.Context
import be.tim.beers.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var servicesApiInterface: ApiService? = null

    fun build(token:String): ApiService? {
        var builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        var httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(AuthInterceptor(null))
//        httpClient.addInterceptor(interceptor())

        var retrofit: Retrofit = builder.client(httpClient.build()).build()
        servicesApiInterface = retrofit.create(
            ApiService::class.java
        )

        return servicesApiInterface as ApiService
    }

//    private fun interceptor(): HttpLoggingInterceptor {
//        val httpLoggingInterceptor = HttpLoggingInterceptor()
//        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        return httpLoggingInterceptor
//    }
}