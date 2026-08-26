package com.gamefactory.hexflowconnect;

import java.util.ArrayList;
import java.util.List;

/**
 * Hex board model using axial coordinates (q, r).
 * Cells can be: EMPTY, NODE(color), BLOCKER, or a path cell.
 * Single-stroke path connects matching-colored nodes in user-defined order.
 */
public class HexBoard {
    public static final int EMPTY = 0;
    public static final int NODE = 1;
    public static final int BLOCKER = 2;

    public static final int[] DR = {-1, -1, 0, 1, 1, 0};
    public static final int[] DQ = {0, 1, 1, 0, -1, -1};

    public static class Cell {
        public int type = EMPTY;
        public int color = 0; // 1..N
        public final int q, r;
        public Cell(int q, int r) { this.q = q; this.r = r; }
    }

    public final int radius;
    public final Cell[][] grid; // [q + radius][r + radius]
    public final List<Cell> nodes = new ArrayList<>();

    public HexBoard(int radius) {
        this.radius = radius;
        this.grid = new Cell[2 * radius + 1][2 * radius + 1];
        for (int q = -radius; q <= radius; q++) {
            for (int r = -radius; r <= radius; r++) {
                if (Math.abs(q + r) > radius) continue;
                grid[q + radius][r + radius] = new Cell(q, r);
            }
        }
    }

    public Cell cellAt(int q, int r) {
        if (Math.abs(q + r) > radius) return null;
        if (q < -radius || q > radius || r < -radius || r > radius) return null;
        return grid[q + radius][r + radius];
    }

    public boolean inBounds(int q, int r) {
        return cellAt(q, r) != null;
    }

    /** Add a colored node. color 1..N. */
    public void addNode(int q, int r, int color) {
        Cell c = cellAt(q, r);
        if (c == null) return;
        c.type = NODE;
        c.color = color;
        nodes.add(c);
    }

    public void addBlocker(int q, int r) {
        Cell c = cellAt(q, r);
        if (c == null) return;
        c.type = BLOCKER;
    }

    public boolean isAdjacent(Cell a, Cell b) {
        for (int i = 0; i < 6; i++) {
            if (a.q + DQ[i] == b.q && a.r + DR[i] == b.r) return true;
        }
        return false;
    }
}
