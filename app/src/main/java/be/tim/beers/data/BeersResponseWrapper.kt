package be.tim.beers.data

import com.google.gson.annotations.SerializedName

class BeersResponseWrapper (
    @field:SerializedName("data") var data: List<Beer>,
)