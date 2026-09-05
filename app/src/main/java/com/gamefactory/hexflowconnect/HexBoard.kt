package com.gamefactory.hexflowconnect

/**
 * Hex cell using axial coordinates (q, r).
 * Cube constraint: q + r + s = 0, s = -(q+r).
 */
data class HexCell(val q: Int, val r: Int) {
    val s: Int get() = -(q + r)

    /** The six axial neighbor directions */
    companion object {
        val DIRECTIONS = listOf(
            HexCell(1, 0),   // E
            HexCell(1, -1),  // NE
            HexCell(0, -1),  // NW
            HexCell(-1, 0),  // W
            HexCell(-1, 1),  // SW
            HexCell(0, 1)    // SE
        )
    }

    /** True if two hex cells share a side (adjacent) */
    fun isAdjacentTo(other: HexCell): Boolean {
        val dq = kotlin.math.abs(this.q - other.q)
        val dr = kotlin.math.abs(this.r - other.r)
        // In axial coords, distance = max(|dq|, |dr|, |dq+dr|)
        return maxOf(dq, dr, kotlin.math.abs((this.q + this.r) - (other.q + other.r))) == 1
    }

    /** All six neighbor cells */
    fun neighbors(): List<HexCell> = DIRECTIONS.map { HexCell(this.q + it.q, this.r + it.r) }

    override fun equals(other: Any?): Boolean = other is HexCell && other.q == q && other.r == r
    override fun hashCode(): Int = 31 * q + r
}
