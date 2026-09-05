package com.gamefactory.hexflowconnect

/**
 * Core game engine for Hex Flow Connect.
 *
 * Rules:
 * - Player draws a single continuous path across a hex grid.
 * - Each step must be to an adjacent hex cell.
 * - No cell may be visited twice (no backtracking).
 * - Path must start and end on matching colored endpoints.
 * - Bridges allow crossing paths of different colors.
 */
class GameEngine {
    data class PathResult(
        val valid: Boolean,
        val length: Int,
        val errorMessage: String? = null
    )

    /**
     * Validate a drawn path against the rules.
     * @param path Ordered list of HexCells forming the path.
     * @return PathResult with validity, length, and optional error.
     */
    fun validatePath(path: List<HexCell>): PathResult {
        if (path.size < 2) {
            return PathResult(false, path.size, "Path too short")
        }

        // Check adjacency: each consecutive pair must be neighbors
        for (i in 0 until path.size - 1) {
            if (!path[i].isAdjacentTo(path[i + 1])) {
                return PathResult(
                    false, path.size,
                    "Non-adjacent step: ${path[i]} -> ${path[i + 1]}"
                )
            }
        }

        // Check no backtracking: all cells must be unique
        val unique = path.toSet()
        if (unique.size != path.size) {
            return PathResult(false, path.size, "Path revisits a cell")
        }

        return PathResult(true, path.size, null)
    }

    /**
     * Score a valid path based on par.
     * @param pathLength Number of cells in the path.
     * @param par The target number of moves for this board.
     * @return Stars (1–3) based on how pathLength compares to par.
     */
    fun scorePath(pathLength: Int, par: Int): Int {
        if (pathLength <= par - 1) return 3          // Under par → 3 stars
        if (pathLength <= par + 1) return 2          // Par ± 1 → 2 stars
        return 1                                       // Over par+1 → 1 star
    }

    /**
     * Check if a cell is a valid starting position for a new path.
     * (Currently all non-blocked cells are valid starts.)
     */
    fun isValidStart(cell: HexCell): Boolean = true
}
