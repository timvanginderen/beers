package be.tim.beers.util

import be.tim.beers.Constants.COORDINATE_BASE_MULTIPLIER

object Util {

    fun convertLocationToLong(coordinate: Double) : Long {
        return (coordinate * COORDINATE_BASE_MULTIPLIER).toLong()
    }

    fun convertLocationToDouble(coordinate: Long) : Double {
        return coordinate.toDouble()/COORDINATE_BASE_MULTIPLIER
    }
}