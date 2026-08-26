package com.gamefactory.hexflowconnect;

import org.junit.Test;
import static org.junit.Assert.*;

public class HexBoardTest {
    @Test
    public void testCellAtBounds() {
        HexBoard board = new HexBoard(2);
        HexBoard.Cell c = board.cellAt(0, 0);
        assertNotNull(c);
        assertEquals(0, c.q);
        assertEquals(0, c.r);
        assertNull(board.cellAt(3, 0)); // out of bounds
    }

    @Test
    public void testNodePlacement() {
        HexBoard board = new HexBoard(1);
        board.addNode(0, 0, 1);
        HexBoard.Cell c = board.cellAt(0, 0);
        assertEquals(HexBoard.NODE, c.type);
        assertEquals(1, c.color);
    }

    @Test
    public void testAdjacent() {
        HexBoard board = new HexBoard(2);
        HexBoard.Cell a = board.cellAt(0, 0);
        HexBoard.Cell b = board.cellAt(1, 0);
        assertTrue(board.isAdjacent(a, b));
        assertFalse(board.isAdjacent(a, board.cellAt(2, 0))); // not adjacent
    }

    @Test
    public void testSeededBoardFactory() {
        long seed = 20260826L;
        SeededBoardFactory factory = new SeededBoardFactory(seed);
        HexBoard board = factory.generate(2, 2, 2);
        int nodeCount = 0;
        for (int q = -2; q <= 2; q++) {
            for (int r = -2; r <= 2; r++) {
                if (Math.abs(q + r) > 2) continue;
                HexBoard.Cell c = board.cellAt(q, r);
                if (c != null && c.type == HexBoard.NODE) nodeCount++;
            }
        }
        assertEquals(2, nodeCount);
        // deterministic
        HexBoard board2 = factory.generate(2, 2, 2);
        assertEquals(board.nodes.size(), board2.nodes.size());
    }

    @Test
    public void testPathValidatorSimple() {
        HexBoard board = new HexBoard(1);
        board.addNode(0, 0, 1);
        board.addNode(0, -1, 1);
        PathValidator pv = new PathValidator(board);
        HexBoard.Cell start = board.cellAt(0, 0);
        HexBoard.Cell end = board.cellAt(0, -1);
        assertTrue(pv.canVisit(null, start));
        pv.visit(start);
        assertTrue(pv.canVisit(start, end));
        pv.visit(end);
        assertTrue(pv.isVictory());
    }
}