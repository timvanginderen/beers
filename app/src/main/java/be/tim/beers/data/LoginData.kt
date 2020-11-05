package be.tim.beers.data

import com.google.gson.annotations.SerializedName

class LoginData (
    @field:SerializedName("accessToken") var accessToken: String,
    @field:SerializedName("refreshToken") var refreshToken: String,
){

}