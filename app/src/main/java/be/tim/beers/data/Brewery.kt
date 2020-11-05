package be.tim.beers.data

import com.google.gson.annotations.SerializedName

class Brewery (
    @field:SerializedName("id") var id: String,
    @field:SerializedName("name") var name: String,
    @field:SerializedName("address") var address: String,
    @field:SerializedName("city") var city: String,
    @field:SerializedName("country") var country: String,
)