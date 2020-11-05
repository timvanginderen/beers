package be.tim.beers.data

import com.google.gson.annotations.SerializedName

class ResponseWrapper<E> (
    @field:SerializedName("data") var data: E,
)