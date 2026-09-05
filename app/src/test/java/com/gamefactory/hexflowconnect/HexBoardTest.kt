package com.gamefactory.hexflowconnect

import org.junit.Test
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HexBoardTest {
    @Test
    fun adjacency_Test() {
        val a = HexCell(0, 0)
        val b = HexCell(1, 0)
        assertTrue(a.isAdjacentTo(b))
        assertTrue(b.isAdjacentTo(a))
    }

    @Test
    fun seed_ForceDeterminism() {
        val d = LocalDate.of(2026, 9, 2)
        val s1 = DailySeedGenerator.seedForDate(d)
        val s2 = DailySeedGenerator.seedForDate(d)
        assertEquals(s1, s2)
    }

    @Test
    fun path_Valid() {
        val engine = GameEngine()
        val path = listOf(HexCell(0,0), HexCell(1,0), HexCell(1,-1))
        val result = engine.validatePath(path)
        assertTrue(result.valid)
    }

    @Test
    fun path_BacktrackInvalid() {
        val engine = GameEngine()
        val path = listOf(HexCell(0,0), HexCell(1,0), HexCell(0,0))
        val result = engine.validatePath(path)
        assertEquals(false, result.valid)
    }
}
