package be.tim.beers.data

import com.google.gson.annotations.SerializedName

class Beer (
    @field:SerializedName("id") var id: String,
    @field:SerializedName("name") var name: String,
    @field:SerializedName("thumbImageUrl") var thumbImageUrl: String,
    @field:SerializedName("imageUrl") var imageUrl: String,
    @field:SerializedName("brewery") var brewery: Brewery,
)