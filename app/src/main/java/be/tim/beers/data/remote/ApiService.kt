package be.tim.beers.data.remote

import be.tim.beers.data.BeersResponseWrapper
import be.tim.beers.data.LoginResponse
import be.tim.beers.data.UserInfo
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("auth/login")
    fun login(@Body userInfo: UserInfo) : Call<LoginResponse>

    @GET("beers")
    fun getBeers(@Header("Authorization") token: String) : Call<BeersResponseWrapper>
}