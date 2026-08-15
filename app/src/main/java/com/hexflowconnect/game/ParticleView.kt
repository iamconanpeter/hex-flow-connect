/**
 * ParticleView - Success particle effects overlay
 * 
 * Displays confetti-style particles when a circuit is successfully completed
 */

package com.hexflowconnect.game

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class ParticleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val particles = mutableListOf<Particle>()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val random = kotlin.random.Random

    private var animating = false
    private val animator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 1000
        addUpdateListener { invalidate() }
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                animating = false
                particles.clear()
            }
        })
    }

    /**
     * Start the particle animation
     * @param count number of particles (default 50)
     * @param centerX center X for emission (default center)
     * @param centerY center Y for emission (default center)
     */
    fun startParticles(
        count: Int = 50,
        centerX: Float = width.toFloat() / 2f,
        centerY: Float = height.toFloat() / 2f
    ) {
        if (animating) {
            animator.cancel()
        }

        particles.clear()
        repeat(count) {
            particles.add(Particle(
                x = centerX,
                y = centerY,
                vx = (random.nextFloat() - 0.5f) * 12f,
                vy = (random.nextFloat() - 0.5f) * 12f - 6f,
                color = randomColor(),
                size = random.nextFloat() * 6f + 2f,
                life = random.nextFloat() * 300f + 200f
            ))
        }

        animating = true
        animator.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!animating || particles.isEmpty()) return

        val delta = 16f // ~60fps
        particles.forEach { particle ->
            particle.update(delta)
            particle.draw(canvas, paint)
        }
    }

    private fun randomColor(): Int {
        return listOf(
            Color.parseColor("#FF00FF"), // Neon magenta
            Color.parseColor("#00FFFF"), // Neon cyan
            Color.parseColor("#FFFF00"), // Neon yellow
            Color.parseColor("#FF0066")  // Neon pink
        ).random()
    }

    private data class Particle(
        var x: Float,
        var y: Float,
        val vx: Float,
        val vy: Float,
        val color: Int,
        val size: Float,
        var life: Float
    ) {
        fun update(delta: Float) {
            x += vx * delta * 0.016f
            y += vy * delta * 0.016f
            life -= delta
        }

        fun draw(canvas: Canvas, paint: Paint) {
            paint.color = color
            paint.alpha = (255 * (life / 500f).coerceIn(0f, 1f)).toInt()
            canvas.drawCircle(x, y, size, paint)
        }
    }
}