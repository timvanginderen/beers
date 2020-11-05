package be.tim.beers.data

import com.google.gson.annotations.SerializedName

class BreweryResponseWrapper (
    @field:SerializedName("data") var data: Brewery,
)