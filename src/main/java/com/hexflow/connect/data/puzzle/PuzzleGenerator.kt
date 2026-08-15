package com.hexflow.connect.data.puzzle

import com.hexflow.connect.domain.hex.HexCoordinate
import kotlin.random.Random

/**
 * Generates daily puzzle content using deterministic seeding
 */
class PuzzleGenerator {
    /**
     * Generates a puzzle based on the given seed and difficulty
     * @param seed the seed for deterministic generation
     * @param difficulty the difficulty level (1-5)
     */
    fun generatePuzzle(seed: Long, difficulty: Int = 3): GeneratedPuzzle {
        val random = Random(seed)
        
        // Grid size based on difficulty
        val gridSize = 3 + difficulty
        
        // Generate node positions
        val nodes = mutableListOf<PuzzleNode>()
        val validPositions = generateValidPositions(gridSize, random)
        
        // Assign colors to nodes
        val colors = listOf(PuzzleNode.NodeColor.RED, PuzzleNode.NodeColor.GREEN, 
                           PuzzleNode.NodeColor.BLUE, PuzzleNode.NodeColor.YELLOW, 
                           PuzzleNode.NodeColor.CYAN, PuzzleNode.NodeColor.MAGENTA)
        
        for (position in validPositions) {
            val color = colors[random.nextInt(colors.size)]
            nodes.add(PuzzleNode(position, color))
        }
        
        // Generate bridges
        val bridges = generateBridges(validPositions, random, gridSize)
        
        // Generate blockers
        val blockers = generateBlockers(validPositions, random, gridSize)
        
        // Find matching pairs
        val matchingPairs = generateMatchingPairs(nodes, random, gridSize)
        
        // Calculate par metrics
        val parTimeSeconds = calculateParTime(gridSize, matchingPairs.size, difficulty)
        val parMoves = calculateParMoves(matchingPairs.size, difficulty)
        
        // Generate solution path for validation
        val solution = generateSolution(matchingPairs, bridges, blockers, random)
        
        return GeneratedPuzzle(
            id = "puzzle_${seed}",
            seed = seed,
            gridSize = gridSize,
            nodes = nodes,
            bridges = bridges,
            blockers = blockers,
            matchingPairs = matchingPairs,
            parTimeSeconds = parTimeSeconds,
            parMoves = parMoves,
            solution = solution,
            difficulty = difficulty
        )
    }

    /**
     * Generates valid positions for nodes based on grid size
     */
    private fun generateValidPositions(gridSize: Int, random: Random): List<HexCoordinate> {
        val positions = mutableListOf<HexCoordinate>()
        val totalPositions = (gridSize * 2 + 1)
        
        while (positions.size < totalPositions * 0.7) {
            val q = random.nextInt(-gridSize, gridSize + 1)
            val r = random.nextInt(-gridSize, gridSize + 1)
            
            if ((q + r) % 2 == 0) {
                val coordinate = HexCoordinate(q, r)
                if (!positions.contains(coordinate)) {
                    positions.add(coordinate)
                }
            }
        }
        
        return positions
    }

    /**
     * Generates bridges between nodes
     */
    private fun generateBridges(positions: List<HexCoordinate>, random: Random, gridSize: Int): Set<Bridge> {
        val bridges = mutableSetOf<Bridge>()
        
        if (random.nextBoolean()) {
            for (position in positions) {
                if (random.nextDouble() < 0.3) {
                    val bridge = Bridge(position, random.nextBoolean())
                    bridges.add(bridge)
                }
            }
        }
        
        return bridges
    }

    /**
     * Generates blockers that affect path traversal
     */
    private fun generateBlockers(positions: List<HexCoordinate>, random: Random, gridSize: Int): Set<Blocker> {
        val blockers = mutableSetOf<Blocker>()
        
        if (random.nextBoolean()) {
            for (position in positions) {
                if (random.nextDouble() < 0.2) {
                    val blocker = Blocker(position, random.nextBoolean())
                    blockers.add(blocker)
                }
            }
        }
        
        return blockers
    }

    /**
     * Generates matching pairs for the puzzle
     */
    private fun generateMatchingPairs(
        nodes: List<PuzzleNode>, 
        random: Random, 
        gridSize: Int
    ): List<Pair<HexCoordinate, HexCoordinate>> {
        val pairs = mutableListOf<Pair<HexCoordinate, HexCoordinate>>()
        
        if (nodes.size >= 4) {
            val shuffledNodes = nodes.shuffled(random)
            for (i in 0 until minOf(3, nodes.size / 2)) {
                val firstIndex = i * 2
                val secondIndex = firstIndex + 1
                val firstNode = shuffledNodes[firstIndex]
                val secondNode = shuffledNodes[secondIndex]
                
                // Ensure nodes are not adjacent
                if (!areNodesAdjacent(firstNode.coordinate, secondNode.coordinate, gridSize)) {
                    pairs.add(firstNode.coordinate to secondNode.coordinate)
                }
            }
        }
        
        return pairs
    }

    /**
     * Generates the solution path for validation
     */
    private fun generateSolution(
        pairs: List<Pair<HexCoordinate, HexCoordinate>>, 
        bridges: Set<Bridge>, 
        blockers: Set<Blocker>, 
        random: Random
    ): List<HexCoordinate> {
        val solution = mutableListOf<HexCoordinate>()
        
        for ((first, second) in pairs) {
            val path = generatePathBetweenNodes(first, second, bridges, blockers, random)
            solution.addAll(path)
        }
        
        return solution
    }

    /**
     * Calculates the par time based on puzzle complexity
     */
    private fun calculateParTime(gridSize: Int, pairCount: Int, difficulty: Int): Int {
        return (gridSize * pairCount * 5 * difficulty) + 30
    }

    /**
     * Calculates the par moves based on puzzle complexity
     */
    private fun calculateParMoves(pairCount: Int, difficulty: Int): Int {
        return pairCount * 2 + difficulty * 5
    }

    /**
     * Generates a path between two nodes
     */
    private fun generatePathBetweenNodes(
        first: HexCoordinate,
        second: HexCoordinate,
        bridges: Set<Bridge>,
        blockers: Set<Blocker>,
        random: Random
    ): List<HexCoordinate> {
        val path = mutableListOf(first)
        
        // Simple A* pathfinding for demonstration
        val queue = mutableListOf(first)
        val visited = mutableSetOf(first)
        
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            
            if (current == second) {
                path.add(current)
                return path
            }
            
            for (neighbor in current.neighbors()) {
                // Check if neighbor is blocked
                if (isBlocked(neighbor, blockers)) continue
                
                // Check if path is valid
                if (!isPathBlocked(current, neighbor, bridges, blockers)) {
                    visited.add(neighbor)
                    queue.add(neighbor)
                    path.add(neighbor)
                }
            }
        }
        
        // Fallback: direct line if no path found
        path.add(second)
        return path
    }

    /**
     * Checks if a node is blocked by a blocker
     */
    private fun isBlocked(coordinate: HexCoordinate, blockers: Set<Blocker>): Boolean {
        return blockers.any { it.coordinate == coordinate }
    }

    /**
     * Checks if path between two nodes is blocked by bridges or blockers
     */
    private fun isPathBlocked(
        first: HexCoordinate, 
        second: HexCoordinate,
        bridges: Set<Bridge>, 
        blockers: Set<Blocker>
    ): Boolean {
        if (isBlocked(first, blockers) || isBlocked(second, blockers)) {
            return true
        }
        
        return bridges.any { it.active && (it.coordinate == first || it.coordinate == second) }
    }

    /**
     * Checks if two nodes are adjacent
     */
    private fun areNodesAdjacent(
        first: HexCoordinate, 
        second: HexCoordinate,
        gridSize: Int
    ): Boolean {
        return first.distanceTo(second) <= 2
    }
}

/**
 * Represents a generated puzzle
 */
data class GeneratedPuzzle(
    val id: String,
    val seed: Long,
    val gridSize: Int,
    val nodes: List<PuzzleNode>,
    val bridges: Set<Bridge>,
    val blockers: Set<Blocker>,
    val matchingPairs: List<Pair<HexCoordinate, HexCoordinate>>,
    val parTimeSeconds: Int,
    val parMoves: Int,
    val solution: List<HexCoordinate>,
    val difficulty: Int
)

/**
 * Represents a node in the puzzle
 */
data class PuzzleNode(
    val coordinate: HexCoordinate,
    val color: NodeColor
) {
    enum class NodeColor {
        RED, GREEN, BLUE, YELLOW, CYAN, MAGENTA, ORANGE, PURPLE, DEFAULT
    }
}

/**
 * Represents a bridge in the puzzle
 */
data class Bridge(
    val coordinate: HexCoordinate,
    val active: Boolean
)

/**
 * Represents a blocker in the puzzle
 */
data class Blockers(
    val coordinate: HexCoordinate,
    val active: Boolean
)