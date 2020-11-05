package be.tim.beers.data

import com.google.gson.annotations.SerializedName

class UserInfo (
    @SerializedName("username") val userName: String?,
    @SerializedName("password") val password: String?,
)
