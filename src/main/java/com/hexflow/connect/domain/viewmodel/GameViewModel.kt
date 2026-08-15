package com.hexflow.connect.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.LiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.hexflow.connect.domain.puzzle.*
import com.hexflow.connect.domain.hex.*

/**
 * ViewModel for the Hex Flow Connect game
 * Manages game state and business logic
 */
class GameViewModel : ViewModel() {
    // Game state LiveData
    private val _gameState = MutableStateFlow<CreatedGameState>(GameStateConfig())
    val gameState: StateFlow<CreatedGameState> = _gameState

    /**
     * Creates a new game with the specified seed
     * @param seed the seed for deterministic puzzle generation
     */
    fun createGame(seed: Long) {
        // Generate puzzle using deterministic seed
        val generator = PuzzleGenerator()
        val puzzle = generator.generatePuzzle(seed)
        
        // Initialize game state
        val state = GameState(puzzle)
        _gameState.value = GameState(puzzle)
    }

    /**
     * Advances to the next puzzle in the sequence
     */
    fun nextPuzzle() {
        // This would normally generate the next puzzle based on seed
        // For now, just create a new one with incremented seed
        val nextSeed = _gameState.value.value.seed + 1
        createGame(nextSeed)
    }

    /**
     * Validates a move in the game
     * @param move the move to validate
     */
    fun validateMove(move: Move): ValidationResult {
        val currentState = _gameState.value.value
        val currentPath = currentState.path
        val puzzle = currentState.puzzle
        
        // Check if move is valid according to game rules
        if (isValidMove(move, currentPath, currentState.path)) {
            return ValidationResult.Valid
        } else {
            return ValidationResult.Invalid("Invalid move")
        }
    }

    /**
     * Checks if the current puzzle is solved
     */
    fun isPuzzleSolved(): Boolean {
        val currentState = _gameState.value.value
        val puzzle = currentState.puzzle
        val completedPairs = currentState.completedPairs
        return completedPairs.size == puzzle.matchingPairs.size
    }

    /**
     * Validates a path between two nodes
     */
    private fun validatePathBetweenNodes(
        start: HexCoordinate,
        end: HexCoordinate,
        path: List<HexCoordinate>
    ): ValidationResult {
        // Validate path according to game rules
        // Implementation would go here
        return ValidationResult.Valid
    }
}

/**
 * Represents the state of a created game
 */
data class CreatedGameState(
    val puzzle: GeneratedPuzzle,
    val path: MutableList<HexCoordinate> = mutableListOf(),
    val completedPairs: Set<Pair<HexCoordinate, HexCoordinate>> = emptySet(),
    val undoStack: MutableList<GameSnapshot> = mutableListOf()
) {
    val pathEnd: HexCoordinate? = path.lastOrNull()
}

/**
 * Represents the result of a validation check
 */
sealed class ValidationResult {
    object Valid : ValidationResult()
    data class Invalid(val reason: String) : ValidationResult()
}

class GameStateConfig {
    standalone val gameState: CreatedGameState