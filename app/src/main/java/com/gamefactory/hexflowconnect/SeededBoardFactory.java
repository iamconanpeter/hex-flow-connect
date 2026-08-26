package com.gamefactory.hexflowconnect;

import java.util.Random;

/** Deterministic seeded board generator for daily challenges. */
public class SeededBoardFactory {
    private final long seed;
    private final Random rng;

    public SeededBoardFactory(long seed) {
        this.seed = seed;
        this.rng = new Random(seed);
    }

    public HexBoard generate(int radius, int nodeCount, int colorCount) {
        HexBoard board = new HexBoard(radius);
        int placed = 0;
        int attempts = 0;
        while (placed < nodeCount && attempts < radius * radius * 4) {
            int q = rng.nextInt(2 * radius + 1) - radius;
            int r = rng.nextInt(2 * radius + 1) - radius;
            HexBoard.Cell c = board.cellAt(q, r);
            if (c != null && c.type == HexBoard.EMPTY) {
                board.addNode(q, r, 1 + rng.nextInt(colorCount));
                placed++;
            }
            attempts++;
        }
        return board;
    }

    public static long dailySeed(int year, int month, int day) {
        return (long) year * 10000L + (long) month * 100L + day;
    }
}
