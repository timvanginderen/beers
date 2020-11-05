package be.tim.beers.data.remote

import be.tim.beers.data.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("auth/login")
    fun login(@Body userInfo: UserInfo) : Call<LoginResponse>

    @GET("beers")
    fun getBeers() : Call<BeersResponseWrapper>

    @GET("beers/{beerId}")
    fun getBeer(@Path("beerId") beerId: String) : Call<BeerResponseWrapper>

    @PUT("beers/{beerId}")
    fun updateRating(@Path("beerId") beerId: String, @Body ratingInfo: RatingInfo)
            : Call<BeerResponseWrapper>

    @GET("breweries/{breweryId}")
    fun getBrewery(@Path("breweryId") breweryId: String) : Call<BreweryResponseWrapper>
}