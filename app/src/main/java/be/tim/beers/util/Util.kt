package be.tim.beers.util

object Util {

    fun convertLocationToLong(coordinate: Double) : Long {
        return (coordinate * 1000000000).toLong()
    }

    fun convertLocationToDouble(coordinate: Long) : Double {
        return coordinate.toDouble()/1000000000
    }
}