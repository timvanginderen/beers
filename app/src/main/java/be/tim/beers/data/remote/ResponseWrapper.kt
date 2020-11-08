package be.tim.beers.data.remote

import com.google.gson.annotations.SerializedName

class ResponseWrapper<E> (
    @field:SerializedName("data") var data: E,
)