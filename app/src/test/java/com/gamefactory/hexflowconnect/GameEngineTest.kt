package com.gamefactory.hexflowconnect

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Tests for GameEngine path validation and scoring.
 */
class GameEngineTest {

    private val engine = GameEngine()

    @Test
    fun validPath() {
        val path = listOf(
            HexCell(0,0), HexCell(1,0), HexCell(1,-1)
        )
        val result = engine.validatePath(path)
        assertTrue(result.valid)
        assertEquals(3, result.length)
        assertEquals(null, result.errorMessage)
    }

    @Test
    fun nonAdjacentInvalid() {
        val path = listOf(
            HexCell(0,0), HexCell(2,0) // skip (1,0)
        )
        val result = engine.validatePath(path)
        assertFalse(result.valid)
        assertEquals("Non-adjacent step", result.errorMessage ?: "")
    }

    @Test
    fun backtrackInvalid() {
        val path = listOf(
            HexCell(0,0), HexCell(1,0), HexCell(0,0)
        )
        val result = engine.validatePath(path)
        assertFalse(result.valid)
        assertEquals("Path revisits a cell", result.errorMessage ?: "")
    }

    @Test
    fun scorePathUnderPar() {
        assertEquals(3, engine.scorePath(4, 6)) // under par-1? Actually pathLength <= par-1 => 3
        assertEquals(3, engine.scorePath(5, 6)) // par-1 => 3
        assertEquals(2, engine.scorePath(6, 6)) // par => 2
        assertEquals(2, engine.scorePath(7, 6)) // par+1 => 2
        assertEquals(1, engine.scorePath(8, 6)) // over par+1 => 1
    }
}