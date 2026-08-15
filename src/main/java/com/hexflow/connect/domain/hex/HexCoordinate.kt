package com.hexflow.connect.domain.hex

/**
 * Represents a hexagon coordinate using the axial coordinate system (q, r)
 * where s = -q - r is the implicit third coordinate
 */
data class HexCoordinate(val q: Int, val r: Int) {
    val s: Int get() = -q - r

    /**
     * Returns the six neighboring hex coordinates
     */
    fun neighbors(): List<HexCoordinate> {
        return listOf(
            HexCoordinate(q + 1, r),     // East
            HexCoordinate(q + 1, r - 1), // Northeast
            HexCoordinate(q, r - 1),     // Northwest
            HexCoordinate(q - 1, r),     // West
            HexCoordinate(q - 1, r + 1), // Southwest
            HexCoordinate(q, r + 1)      // Southeast
        )
    }

    /**
     * Calculates the distance between two hex coordinates
     */
    fun distanceTo(other: HexCoordinate): Int {
        return (abs(q - other.q) + abs(r - other.r) + abs(s - other.s)) / 2
    }

    /**
     * Converts hex coordinates to pixel coordinates for pointy-top orientation
     * @param size the size (radius) of each hex
     */
    fun toPixel(size: Double): Pair<Double, Double> {
        val x = size * Math.sqrt(3) * (q + r / 2.0)
        val y = size * 3.0 / 2.0 * r
        return Pair(x, y)
    }

    /**
     * Creates a HexCoordinate from pixel coordinates
     * @param x x-coordinate in pixels
     * @param y y-coordinate in pixels
     * @param size the size (radius) of each hex
     */
    companion object {
        fun fromPixel(x: Double, y: Double, size: Double): HexCoordinate {
            val q = (Math.sqrt(3.0) / 3.0 * x - 1.0 / 3.0 * y) / size
            val r = (2.0 / 3.0 * y) / size
            return HexCoordinate(Math.round(q.toFloat()), Math.round(r.toFloat()))
        }
    }

    operator fun plus(other: HexCoordinate): HexCoordinate =
        HexCoordinate(q + other.q, r + other.r)

    operator fun minus(other: HexCoordinate): HexCoordinate =
        HexCoordinate(q - other.q, r - other.r)
}