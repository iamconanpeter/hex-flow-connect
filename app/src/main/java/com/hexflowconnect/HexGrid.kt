package com.hexflowconnect

data class HexCoord(val q: Int, val r: Int)
enum class TileType { EMPTY, START, END, RED, BLUE, GREEN, PURPLE, BRIDGE, BLOCKED }
data class HexGrid(val width: Int, val height: Int, val tiles: Map<HexCoord, TileType> = emptyMap())
