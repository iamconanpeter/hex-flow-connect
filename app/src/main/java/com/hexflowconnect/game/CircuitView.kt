/**
 * CircuitView.kt - Custom view for rendering and interacting with circuit puzzles
 *
 * This view handles drawing the grid, nodes, connections, and player-traced path
 */

package com.hexflowconnect.game

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.*
import kotlin.math.

class CircuitView(
    val context: Context
) : View() {
    // Paints
    private val nodePaint = Paint().apply {
        color = Color.parseColor("#00FFFF") // Neon cyan
        style = Paint.Style.FILL
        antiAlias = true
    }
    
    private val connectionPaint = Paint().apply {
        color = Color.parseColor("#FFFFFF") // White
        style = Paint.Style.STROKE
        strokeWidth = 8f
        antiAlias = true
    }
    
    private val tracePaint = Paint().apply {
        color = Color.parseColor("#FF00FF") // Neon magenta
        style = Paint.Style.STROKE
        strokeWidth = 12f
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        antiAlias = true
    }
    
    private val gridPaint = Paint().apply {
        color = Color.parseColor("#888888") // Light gray grid
        style = Paint.Style.STROKE
        strokeWidth = 2f
        antiAlias = true
    }
    
    // State variables
    private var circuit: Circuit? = null
    private var tracePath = mutableListOf<Pair<Float, Float>>()
    private var touchInProgress = false
    private var lastTouchX = 0f
    private var lastTouchY = 0f
    
    // Callbacks to MainActivity
    private var onTraceComplete: (() -> Unit)? = null
    private var onTouchTooLate: (() -> Unit)? = null

    /**
     * Initialize with circuit data
     * 
     * @param circuit the circuit to render
     */
    fun setCircuit(circuit: Circuit) {
        this.circuit = circuit
        invalidate()
    }
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        // Draw grid
        drawGrid(canvas)
        
        // Draw nodes
        drawNodes(canvas)
        
        // Draw connections
        drawConnections(canvas)
        
        // Draw trace
        drawTrace(canvas)
    }
    
    private fun drawGrid(canvas: Canvas) {
        val cellSize = width.toFloat() / (circuit?.size ?: 7).toFloat()
        
        for (x in 0 until circuit?.size ?: 7) {
            canvas.drawLine(x * cellSize, 0f, x * cellSize, height.toFloat(), gridPaint)
            canvas.drawLine(0f, x * cellSize, width.toFloat(), x * cellSize, gridPaint)
        }
    }
    
    private fun drawNodes(canvas: Canvas) {
        canvas.drawPaint(nodePaint)
        
        circuit?.nodes.forEachIndexed { row, cols ->
            cols.forEachIndexed { col, node ->
                if (node.x != 0 && node.y != 0) { // Draw only valid nodes
                    canvas.drawCircle(
                        node.x.toFloat() * width.toFloat() / (circuit?.size ?: 7).toFloat(),
                        node.y.toFloat() * height.toFloat() / (circuit?.size ?: 7).toFloat(),
                        15f,
                        nodePaint
                    )
                }
            }
        }
    }
    
    private fun drawConnections(canvas: Canvas) {
        circuit?.connections.forEach { conn ->
            drawConnection(conn.from, conn.to, canvas)
        }
    }
    
    private fun drawConnection(from: Circuit.Node, to: Circuit.Node, canvas: Canvas) {
        val startX = from.x.toFloat() * width.toFloat() / (circuit?.size ?: 7).toFloat()
        val startY = from.y.toFloat() * height.toFloat() / (circuit?.size ?: 7).toFloat()
        val endX = to.x.toFloat() * width.toFloat() / (circuit?.size ?: 7).toFloat()
        val endY = to.y.toFloat() * height.toFloat() / (circuit?.size ?: 7).toFloat()
        
        canvas.drawLine(
            startX, startY,
            endX, endY,
            connectionPaint
        )
    }
    
    private fun drawTrace(canvas: Canvas) {
        if (tracePath.isNotEmpty()) {
            val paint = tracePaint.copy().apply {
                alpha = if (tracePath.isNotEmpty()) 1.0f else 0.0f // Simple fade for demo
            }
            
            val paintPath = Path().apply {
                moveTo(tracePath.first().key, tracePath.first().value)
                tracePath.forEachIndexed { i, point ->
                    if (i > 0) {
                        lineTo(point.key, point.value)
                    }
                }
            }
            canvas.drawPath(paintPath, paint)
        }
    }
    
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (circuit != null) {
                    touchInProgress = true
                    val cellSize = width.toFloat() / (circuit?.size ?: 7).toFloat()
                    lastTouchX = (event.x / cellSize).toInt().toFloat()
                    lastTouchY = (event.y / cellSize).toInt().toFloat()
                    
                    tracePath.clear()
                    tracePath.add(Pair(lastTouchX, lastTouchY))
                    invalidate()
                }
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (touchInProgress) {
                    val cellSize = width.toFloat() / (circuit?.size ?: 7).toFloat()
                    val currentX = (event.x / cellSize).toInt().toFloat()
                    val currentY = (event.y / cellSize).toInt().toFloat()
                    
                    tracePath.add(Pair(currentX, currentY))
                    invalidate()
                }
                return true
            }
            MotionEvent.ACTION_UP -> {
                if (touchInProgress) {
                    traceInProgress = false
                    validateTrace()
                    invalidate()
                    return true
                }
                return false
            }
        }
        return super.onTouchEvent(event)
    }
    
    private fun validateTrace() {
        if (circuit != null && tracePath.isNotEmpty()) {
            val isValid = circuit!!.validatePath(tracePath.toList())
            
            if (isValid) {
                onTraceComplete?.invoke()
            } else {
                onTouchTooLate?.invoke()
            }
        }
    }
    
    // Set callbacks from MainActivity
    fun setOnTraceComplete(listener: () -> Unit) {
        onTraceComplete = listener
    }
    fun setOnTouchTooLate(listener: () -> Unit) {
        onTouchTooLate = listener
    }
}