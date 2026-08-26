package com.iamconanpeter.hexflowconnect

data class HexCoordinate(val q: Int, val r: Int) {
    val s: Int = -q - r

    fun neighbors(): List<HexCoordinate> = listOf(
        HexCoordinate(q + 1, r), HexCoordinate(q + 1, r - 1),
        HexCoordinate(q, r - 1), HexCoordinate(q - 1, r),
        HexCoordinate(q - 1, r + 1), HexCoordinate(q, r + 1)
    )

    companion object {
        fun of(q: Int, r: Int) = HexCoordinate(q, r)
    }
}
