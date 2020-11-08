package be.tim.beers

import be.tim.beers.util.Util
import org.junit.Assert
import org.junit.Test

class LocationConverterTest {
    @Test
    fun convertCoordinateToLong_isCorrect() {
        val coordinate = 4.42
        val expected = (Constants.COORDINATE_BASE_MULTIPLIER * coordinate).toLong()
        Assert.assertEquals(expected, Util.convertLocationToLong(coordinate))
    }

    @Test
    fun convertCoordinateToDouble_isCorrect() {
        val coordinate = 4420000000
        val expected = (coordinate.toDouble()/ Constants.COORDINATE_BASE_MULTIPLIER)
        Assert.assertEquals(expected, Util.convertLocationToDouble(coordinate), 0.0)
    }
}