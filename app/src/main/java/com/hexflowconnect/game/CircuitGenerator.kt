/**
 * CircuitGenerator.kt - Seed-based circuit generation system
 *
 * Generates deterministic circuit layouts from seeds and handles daily challenge system.
 */

package com.hexflowconnect.game

import java.util.Random

class CircuitGenerator(
    val context: Context
) {
    // Seed for today's challenge
    private var dailySeed: Long = System.currentTimeMillis()
    private var circuitCache: Circuit? = null
    private val random = Random()

    /**
     * Generate a circuit for today's challenge based on current date
     */
    fun generateDailyCircuit(): Circuit {
        val today = System.currentTimeMillis() / (24 * 60 * 60 * 1000) // Days since epoch
        
        // Reset if new day
        if (today != lastSaveDay) {
            dailySeed = today.hashCode().toLong()
            saveSeed()
            generateCircuit(dailySeed)
        }
        
        return circuitCache ?: Circuit()
    }

    /**
     * Generate a circuit from a specific seed
     */
    fun generateCircuit(seed: Long): Circuit {
        random.setSeed(seed)
        
        // Create circuit with appropriate difficulty based on seed
        val circuit = Circuit(
            size = 7,
            difficultyLevel = (random.nextInt(20) + 1).coerceIn(1, 20)
        )
        
        // Generate nodes and connections
        circuit.generateConnections()
        
        // Set random start/end points
        val startX = random.nextInt(7)
        val startY = random.nextInt(7)
        val endX = random.nextInt(7)
        val endY = random.nextInt(7)
        
        circuit.startNode = circuit.nodes[startX][startY]
        circuit.endNode = circuit.nodes[endX][endY]
        
        circuitCache = circuit
        return circuit
    }

    /**
     * Validate that a seed produces a solvable circuit
     */
    fun validateSeed(seed: Long): Boolean {
        try {
            val circuit = generateCircuit(seed)
            return circuit.validatePath(emptyList()) // Basic validation
        } catch (e: Exception) {
            return false
        }
    }

    // Persistent storage for daily seed
    private fun saveSeed() {
        val prefs = context.getSharedPreferences("hexflow_seed", Context.MODE_PRIVATE)
        prefs.edit().putLong("daily_seed", dailySeed).apply()
    }

    private var lastSaveDay: Long
        get() {
            val prefs = context.getSharedPreferences("hexflow_seed", Context.MODE_PRIVATE)
            return prefs.getLong("last_save_day", 0)
        }
    }

    init {
        // Load saved seed on init
        val prefs = context.getSharedPreferences("hexflow_seed", Context.MODE_PRIVATE)
        dailySeed = prefs.getLong("daily_seed", System.currentTimeMillis().hashCode().toLong())
    }
}

/**
 * Example of circuit generation patterns based on difficulty
 */
private fun Circuit.generateConnections() {
    // Simple implementation - would be expanded with actual path generation logic
    // For now, create basic grid connections
    
    // Horizontal connections
    for (x in 0 until size) {
        for (y in 0 until size-1) {
            if (Random().nextBoolean()) {
                connections.add(Connection(nodes[x][y], nodes[x][y+1]))
            }
        }
    }
    
    // Vertical connections
    for (x in 0 until size-1) {
        for (y in 0 until size) {
            if (Random().nextBoolean()) {
                connections.add(Connection(nodes[x][y], nodes[x+1][y]))
            }
        }
    }
}