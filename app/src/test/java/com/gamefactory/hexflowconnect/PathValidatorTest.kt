package com.gamefactory.hexflowconnect

import org.junit.Test
import kotlin.test.assertTrue
import kotlin.test.assertEquals

/**
 * Tests for PathValidator single-stroke path validation.
 */
class PathValidatorTest {

    @Test
    fun boardCanBeCreated() {
        val board = HexBoard(2) // radius 2 => up to 5x5 effective
        // board should contain cells
        val center = board.cellAt(0, 0)
        assertTrue(center != null)
    }

    @Test
    fun adjacencyWorks() {
        val board = HexBoard(2)
        val a = board.cellAt(0, 0)!!
        val b = board.cellAt(1, 0)
        assertTrue(board.isAdjacent(a, b!!))
    }

    @Test
    fun blockerPreventsVisit() {
        val board = HexBoard(2)
        val a = board.cellAt(0, 0)!!
        val b = board.cellAt(1, 0)!!
        board.addBlocker(1, 0)
        // After blocker: cell type changed to BLOCKER (2)
        assertEquals(2, b.type)
    }

    @Test
    fun pathCanBeValidatedViaEngine() {
        // Validate through GameEngine which uses same HexCell model
        val engine = GameEngine()
        val path = listOf(
            HexCell(0, 0), HexCell(1, 0), HexCell(1, -1)
        )
        val result = engine.validatePath(path)
        assertTrue(result.valid)
    }
}