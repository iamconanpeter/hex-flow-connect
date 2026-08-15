/**
 * PathValidator.kt - Core path validation logic
 *
 * Handles the validation of player-traced paths against the circuit structure.
 */

package com.hexflowconnect.game

import android.graphics.Path

class PathValidator(private val circuit: Circuit) {

    /**
     * Validates if a traced path is correct
     * 
     * @param tracePath the path drawn by the player
     * @param startX starting X coordinate
     * @param startY starting Y coordinate
     * @param endX ending X coordinate
     * @param endY ending Y coordinate
     * @return true if path is valid, false otherwise
     */
    fun validatePath(tracePath: List<Pair<Float, Float>>): Boolean {
        // Basic validation
        if (tracePath.isEmpty()) return false
        if (tracePath.size < 2) return false
        
        // Check if path connects start to end
        val startValid = tracePath[0].key == circuit.startNode?.x && 
                         tracePath[0].value == circuit.startNode?.y
        val endValid = tracePath.last().key == circuit.endNode?.x && 
                       tracePath.last().value == circuit.endNode?.y
        
        if (!startValid || !endValid) return false
        
        // Validate each segment
        for (i in 1 until tracePath.size) {
            val prev = tracePath[i-1]
            val curr = tracePath[i]
            
            // Check if segment is valid
            if (!isValidPathSegment(prev, curr)) return false
        }
        
        // Check if all required nodes are visited
        if (!hasVisitedRequiredNodes(tracePath)) return false
        
        return true
    }

    /**
     * Checks if a segment between two points is valid
     */
    private fun isValidPathSegment(p1: Pair<Float, Float>, p2: Pair<Float, Float>): Boolean {
        // Check if points are on valid nodes
        val fromNode = findNearestNode(p1)
        val toNode = findNearestNode(p2)
        
        if (fromNode == null || toNode == null) return false
        
        // Check direct connection
        return connectionsBetween(fromNode, toNode)
    }

    /**
     * Finds the nearest node to a given point
     */
    private fun findNearestNode(p: Pair<Float, Float>): Node? {
        var nearestNode: Node? = null
        var minDistance = Float.MAX_VALUE
        
        for (row in 0 until circuit.size) {
            for (col in 0 until circuit.size) {
                val node = circuit.nodes[row][col]
                val distance = (p.key - node.x).pow(2) + (p.value - node.y).pow(2)
                
                if (distance < minDistance) {
                    minDistance = distance
                    nearestNode = node
                }
            }
        }
        
        return nearestNode
    }

    /**
     * Checks if a path segment is valid
     */
    private fun isValidPathSegment(p1: Pair<Float, Float>, p2: Pair<Float, Float>): Boolean {
        val fromNode = findNearestNode(p1)
        val toNode = findNearestNode(p2)
        
        return fromNode != null && toNode != null && 
               connectionsBetween(fromNode, toNode).isNotEmpty()
    }

    /**
     * Checks if path visits all required nodes
     */
    private fun hasVisitedRequiredNodes(tracePath: List<Pair<Float, Float>>): Boolean {
        val requiredNodes = mutableSetOf<Node>()
        
        // Collect all nodes in path
        for ((x, y) in tracePath) {
            val node = findNearestNode(p1) { p -> p.key == x && p.value == y }
            if (node != null) {
                requiredNodes.add(node)
            }
        }
        
        // Check if all nodes are connected
        return requiredNodes.all { node ->
            node.connections.any { 
                (it.from == node && it.to in tracePath.map { findNearestNode(it) }) ||
                (it.to == node && it.from in tracePath.map { findNearestNode(it) })
            }
        }
    }

    /**
     * Checks if there's a direct connection between two nodes
     */
    private fun connectionsBetween(from: Node, to: Node): Set<Connection> {
        return connections.filter { 
            (it.from == from && it.to == to) || 
            (it.from == to && it.to == from)
        }
    }

    /**
     * Finds the nearest node to a given coordinate
     */
    private fun findNearestNode(p: Pair<Float, Float>): Node? {
        var nearestNode: Node? = null
        var minDistance = Float.MAX_VALUE
        
        for (row in 0 until circuit.size) {
            for (col in 0 until circuit.size) {
                val node = circuit.nodes[row][col]
                val distance = (p.key - node.x).pow(2) + (p.value - node.y).pow(2)
                
                if (distance < minDistance) {
                    minDistance = distance
                    nearestNode = node
                }
            }
        }
        
        return nearestNode
    }
}