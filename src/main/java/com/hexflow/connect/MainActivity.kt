// Hex Flow Connect MainActivity
package com.hexflow.connect

import io.kotlingx.collection.List
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize puzzle system
        val puzzleSystem = PuzzleSystem()
        puzzleSystem.generatePuzzle()

        // Set up touch listener
        val puzzleView = findViewById