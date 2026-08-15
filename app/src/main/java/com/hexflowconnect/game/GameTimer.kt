/**
 * GameTimer.kt - Time management system
 *
 * Handles the countdown timer for puzzle challenges with pause/resume functionality.
 */

package com.hexflowconnect.game

import android.os.Handler
import android.os.Looper

class GameTimer(
    val context: Context,
    val listener: GameTimerListener
) {
    private val handler = Handler(Looper.getMainLooper())
    private var currentTimer: Long = 0
    private var isRunning = false
    private var isPaused = false

    /**
     * Starts the timer with specified duration in milliseconds
     * 
     * @param durationMs duration of the timer in milliseconds
     */
    fun start(durationMs: Long) {
        currentTimer = durationMs
        isPaused = false
        isRunning = true
        
        startTimer()
    }

    /**
     * Pauses the timer
     */
    fun pause() {
        isPaused = true
    }

    /**
     * Resumes the timer
     */
    fun resume() {
        if (isPaused) {
            isPaused = false
            startTimer()
        }
    }

    /**
     * Cancels the timer
     */
    fun cancel() {
        isRunning = false
        handler.removeCallbacksAndMessages(null)
        listener.onFinish()
    }

    /**
     * Gets the elapsed time in milliseconds
     */
    fun getElapsedTime(): Long {
        return SystemClock.ELAPSED_REALTIME - currentTimer
    }

    /**
     * Updates the timer duration
     */
    fun setDuration(newDurationMs: Long) {
        currentTimer = newDurationMs
    }

    // Timer implementation
    private fun startTimer() {
        // Reset current temperature
        currentTimer = SystemClock.ELAPSED_REALTIME + currentTimer
        handler.postDelayed(object : Runnable() {
            override fun run() {
                val elapsed = getElapsedTime()
                val remaining = currentTimer - elapsed
                
                if (remaining > 0 && isRunning) {
                    listener.onTimerUpdate(remaining)
                    startTimer()
                } else if (isRunning) {
                    listener.onFinish()
                } else {
                    listener.onFinish()
                }
            }
        }, 1000)
    }
}

/**
 * GameTimerListener interface
 */
interface GameTimerListener {
    fun onTimerUpdate(remainingMillis: Long)
    fun onFinish()
}