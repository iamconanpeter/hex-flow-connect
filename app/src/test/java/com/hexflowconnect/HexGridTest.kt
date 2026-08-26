package com.hexflowconnect
import org.junit.Test
import org.junit.Assert.*
class HexGridTest {
    @Test fun gridCreated() {
        val grid = HexGrid(6, 6)
        assertEquals(6, grid.width)
    }
}
