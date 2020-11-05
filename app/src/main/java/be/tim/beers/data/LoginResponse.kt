package be.tim.beers.data

import com.google.gson.annotations.SerializedName

class LoginResponse (
    @field:SerializedName("data") var data: LoginData,
)