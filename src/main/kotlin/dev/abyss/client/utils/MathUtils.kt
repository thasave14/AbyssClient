package dev.abyss.client.utils

import java.math.BigDecimal
import java.math.RoundingMode

object MathUtils {

    fun isInArea(x: Float, y: Float, w: Float, h: Float, x1: Float, y1: Float, w1: Float, h1: Float): Boolean {
        return ((x >= x1) && (x < x1 + w1)) && ((y >= y1) && (y < y1 + h1))
    }

    /**
     * @author soar client owner i think
     */
    fun roundToPlace(value: Double, places: Int): Double {
        require(places >= 0)

        var bd = BigDecimal(value)
        bd = bd.setScale(places, RoundingMode.HALF_UP)
        return bd.toDouble()
    }
}