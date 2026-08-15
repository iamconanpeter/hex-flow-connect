/**
 * MainActivity - Core game logic and UI for Neon Circuit Trace
 */

package com.hexflowconnect.game

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hexflowconnect.game.databinding.ActivityMainBinding
import java.util.Timer
import java.util.UUID

class MainActivity : AppCompatActivity() {
    // Game state
    private enum class GameState {
        IDLE, TRACE, SUCCESS, FAIL
    }
    private var currentState = GameState.FAILURE

    // Configuration
    companion object {
        private const val DEFAULT_BOARD_SIZE = 7
        private const val DEFAULT_TIME_LIMIT_SECONDS = 45
        private const val PARTICLE_COUNT = 50
    }

    // UI Components
    private lateinit var binding: ActivityMainBinding
    private lateinit var circuitView: CircuitView
    private lateinit var particleView: ParticleView
    private lateinit var timerText: TextView
    private lateinit var scoreText: TextView
    private lateinit var levelText: TextView
    private lateinit var hintText: TextView
    private lateinit var skipButton: Button
    private lateinit var retryButton: Button
    private lateinit var nextButton: Button

    // Game Systems
    private var circuitGenerator: CircuitGenerator? = null
    private var gameTimer: GameTimer? = null
    private var scoreManager: ScoreManager? = null
    private var leaderboardManager: LeaderboardManager? = null

    // Timer UI
    private val handler = Handler(Looper.getMainLooper())
    private var timerRunnable: Runnable? = null
    private var currentTimeRemaining: Long = DEFAULT_TIME_LIMIT_SECONDS * 1000
    private var startTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize views
        circuitView = binding.circuitView
        particleView = binding.particleView
        timerText = binding.timerText
        scoreText = binding.scoreText
        levelText = binding.levelText
        hintText = binding.hintText
        skipButton = binding.skipButton
        retryButton = binding.retryButton
        nextButton = binding.nextButton

        // Initialize game systems
        initializeGameSystems()

        // Setup UI listeners
        setupListeners()

        // Start the game
        startNewGame()
    }

    private fun initializeGameSystems() {
        circuitGenerator = CircuitGenerator(this)
        scoreManager = ScoreManager(this)
        leaderboardManager = LeaderboardManager(this)
    }

    private fun setupListeners() {
        skipButton.setOnClickListener {
            if (currentState == GameState.TRACE) {
                endTrace(false)
                showFailure()
            }
        }

        retryButton.setOnClickListener {
            startNewGame()
        }

        nextButton.setOnClickListener {
            startNewGame()
        }

        // Circuit view callbacks
        circuitView.setOnTraceComplete {
            endTrace(true)
            if (currentState == GameState.SUCCESS) {
                showSuccess()
                submitScore()
            }
        }

        circuitView.setOnTouchTooLate {
            if (currentState == GameState.TRACE) {
                // Not an exact error, just a retry opportunity
            }
        }
    }

    private fun startNewGame() {
        currentState = GameState.TRACE
        resetGameState()

        // Generate new circuit
        val circuit = circuitGenerator?.generateDailyCircuit()
        circuitView.setCircuit(circuit!!)

        // Update UI
        val difficulty = circuit?.difficultyLevel ?: 3
        levelText.text = "Day $difficulty"
        timerText.text = formatTime(DEFAULT_TIME_LIMIT_SECONDS * 1000)
        scoreText.text = "Score: ${scoreManager?.currentScore ?: 0}"
        hintText.text = "Trace from source to target before time runs out!"

        // Start timer
        startTimer(DEFAULT_TIME_LIMIT_SECONDS * 1000)
    }

    private fun resetGameState() {
        currentTimeRemaining = DEFAULT_TIME_LIMIT_SECONDS * 1000
        gameTimer?.cancel()
        retryButton.visibility = android.view.View.GONE
        nextButton.visibility = android.view.View.GONE
    }

    private fun startTimer(durationMs: Long) {
        startTimer(durationMs)
    }

    private fun startTimer(durationMs: Long) {
        startTime = System.currentTimeMillis()
        currentTimeRemaining = durationMs

        timerRunnable = object : Runnable {
            override fun run() {
                val elapsed = System.currentTimeMillis() - startTime
                currentTimeRemaining = durationMs - elapsed

                if (currentTimeRemaining > 0) {
                    timerText.text = formatTime(currentTimeRemaining)
                    
                    // Visual warning when time is low
                    if (currentTimeRemaining < 10000) {
                        timerText.setTextColor(0xFFFF4444.toInt()) // Red
                    } else {
                        timerText.setTextColor(0xFFFFFFFF.toInt()) // White
                    }
                    
                    handler.postDelayed(this!!, 50)
                } else {
                    endTrace(false)
                    showFailure()
                }
            }
        }

        handler.post(timerRunnable!!)
    }

    private fun endTrace(success: Boolean) {
        gameTimer?.cancel()
    }

    private fun showSuccess() {
        currentState = GameState.SUCCESS
        retryButton.visibility = android.view.View.VISIBLE
        retryButton.text = "Try Again"

        // Show particles
        particleView.startParticles(PARTICLE_COUNT)

        // Add time bonus
        val timeBonus = (currentTimeRemaining / 1000) * 10
        scoreManager?.addScore(timeBonus)

        // Vibrate for success
        // (would add vibrator service here)
    }

    private fun showFailure() {
        currentState = GameState.FAILURE
        retryButton.visibility = android.view.View.VISIBLE
        retryButton.text = "Retry"
        hintText.text = "Don't give up! Trace the path correctly."
    }

    private fun submitScore() {
        leaderboardManager?.submitScore(
            "daily_challenge",
            scoreManager?.currentScore ?: 0
        )
        scoreManager?.saveHighScore()
    }

    private fun formatTime(ms: Long): String {
        val seconds = ms / 1000
        return String.format("%02d:%02d", seconds / 60, seconds % 60)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(timerRunnable)
        gameTimer?.cancel()
    }
}

class ScoreManager(private val context: Context) {
    private val prefs = context.getSharedPreferences("score_manager", Context.MODE_PRIVATE)
    var currentScore: Int = 0
        private set

    init {
        currentScore = prefs.getInt("current_score", 0)
    }

    fun addScore(points: Int) {
        currentScore += points
        prefs.edit().putInt("current_score", currentScore).apply()
    }

    fun saveHighScore() {
        val highScore = prefs.getInt("high_score", 0)
        if (currentScore > highScore) {
            prefs.edit().putInt("high_score", currentScore).apply()
        }
    }
}

/*leaderboardManager with Google Play Services integration
*/
class LeaderboardManager(private val context: Context) {
    fun submitScore(leaderboardId: String, score: Int) {
        // Would integrate with Google Play Services here
        // For now, just log it
        println("Submitting score $score to $leaderboardId")
    }
}