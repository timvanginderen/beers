package be.tim.beers.data.remote

import be.tim.beers.Constants
import be.tim.beers.data.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST(Constants.LOGIN_ENDPOINT)
    fun login(@Body userInfo: UserInfo) : Call<ResponseWrapper<LoginData>>

    @GET(Constants.BEERS_ENDPOINT)
    fun getBeers() : Call<ResponseWrapper<List<Beer>>>

    @GET("${Constants.BEERS_ENDPOINT}/{beerId}")
    fun getBeer(@Path("beerId") beerId: String) : Call<ResponseWrapper<Beer>>

    @PUT("${Constants.BEERS_ENDPOINT}/{beerId}")
    fun updateRating(@Path("beerId") beerId: String, @Body ratingInfo: RatingInfo)
            : Call<ResponseWrapper<Beer>>

    @GET("${Constants.BREWERIES_ENDPOINT}/{breweryId}")
    fun getBrewery(@Path("breweryId") breweryId: String) : Call<ResponseWrapper<Brewery>>
}