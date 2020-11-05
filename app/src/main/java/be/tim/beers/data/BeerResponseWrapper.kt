package be.tim.beers.data

import com.google.gson.annotations.SerializedName

class BeerResponseWrapper (
    @field:SerializedName("data") var data: Beer,
)