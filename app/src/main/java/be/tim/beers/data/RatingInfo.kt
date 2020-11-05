package be.tim.beers.data

import com.google.gson.annotations.SerializedName

class RatingInfo (
    @SerializedName("rating") val rating: Int,
)