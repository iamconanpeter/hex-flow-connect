package com.gamefactory.hexflowconnect;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Validates a single continuous stroke through hex cells.
 * Rules:
 *  - Each cell visited at most once (no self-intersection).
 *  - Two adjacent cells of same color cancel/clear the line segment.
 *  - Two adjacent cells of differing color "mix" into a new path color (sum mod palette).
 *  - All cells of color must be visited (in any order) for victory.
 */
public class PathValidator {
    private final HexBoard board;
    private final List<HexBoard.Cell> path = new ArrayList<>();
    private final Set<String> visited = new HashSet<>();

    public PathValidator(HexBoard board) {
        this.board = board;
    }

    public List<HexBoard.Cell> getPath() { return path; }

    public boolean canVisit(HexBoard.Cell prev, HexBoard.Cell next) {
        if (next.type == HexBoard.BLOCKER) return false;
        if (visited.contains(key(next))) return false;
        if (prev == null) return true; // first node
        return board.isAdjacent(prev, next);
    }

    public void visit(HexBoard.Cell c) {
        path.add(c);
        visited.add(key(c));
    }

    public void undo() {
        if (path.isEmpty()) return;
        HexBoard.Cell last = path.remove(path.size() - 1);
        visited.remove(key(last));
    }

    public boolean isVictory() {
        Set<Integer> colorsVisited = new HashSet<>();
        for (HexBoard.Cell c : path) {
            if (c.type == HexBoard.NODE) colorsVisited.add(c.color);
        }
        Set<Integer> colorsRequired = new HashSet<>();
        for (HexBoard.Cell n : board.nodes) colorsRequired.add(n.color);
        return colorsVisited.equals(colorsRequired);
    }

    public int parFor(HexBoard.Cell start, HexBoard.Cell goal) {
        // BFS distance
        Set<String> seen = new HashSet<>();
        java.util.Queue<HexBoard.Cell> q = new java.util.LinkedList<>();
        java.util.Map<String, Integer> dist = new java.util.HashMap<>();
        q.add(start);
        dist.put(key(start), 0);
        while (!q.isEmpty()) {
            HexBoard.Cell c = q.poll();
            if (c == goal) return dist.get(key(c));
            for (int i = 0; i < 6; i++) {
                HexBoard.Cell n = board.cellAt(c.q + HexBoard.DQ[i], c.r + HexBoard.DR[i]);
                if (n == null || n.type == HexBoard.BLOCKER) continue;
                String k = key(n);
                if (dist.containsKey(k)) continue;
                dist.put(k, dist.get(key(c)) + 1);
                q.add(n);
            }
        }
        return -1;
    }

    public static String key(HexBoard.Cell c) { return c.q + "," + c.r; }
}
