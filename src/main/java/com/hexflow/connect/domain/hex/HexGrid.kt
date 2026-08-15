package com.hexflow.connect.domain.hex

/**
 * Represents a grid of hexagons organized by radius and center coordinates
 */
class HexGrid(
    val radius: Int = 3,  // half the grid dimension
    val offsetX: Int = 0,
    val offsetY: Int = 0
) {
    private val hexes = Array(radius * 2 + 1) { xArray -> Array(radius * 2 + 1) { yArray -> null as HexCoordinate? } }

    /**
     * Gets the hex coordinate at the specified grid position
     */
    fun getHex(x: Int, y: Int): HexCoordinate? {
        val gridX = offsetX + x + radius
        val gridY = offsetY + y + radius
        return if (gridX in 0..2 * radius && gridY in 0..2 * radius && (gridX + gridY) % 2 == 0) {
            hexes[gridX][gridY]
        } else {
            null
        }
    }

    /**
     * Sets a hex coordinate at the specified grid position
     */
    fun setHex(x: Int, y: Int, coordinate: HexCoordinate) {
        val gridX = offsetX + x + radius
        val gridY = offsetY + y + radius
        if (gridX in 0..2 * radius && gridY in 0..2 * radius && (gridX + gridY) % 2 == 0) {
            hexes[gridX][gridY] = coordinate
        }
    }

    /**
     * Returns all hex coordinates within the grid bounds
     */
    fun allHexes(): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        for ((x, yArray) in hexes.withIndex()) {
            for ((y, coordinate) in yArray.withIndex()) {
                if (coordinate != null) {
                    result.add(Pair(x - radius, y - radius))
                }
            }
        }
        return result
    }
}