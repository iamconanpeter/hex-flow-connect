package com.gamefactory.hexflowconnect

import java.security.MessageDigest
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Generates a deterministic seed from the date string.
 * Format: "YYYY-MM-DD" hashed with SHA-256 truncated to 32 bits.
 */
object DailySeedGenerator {
    private val DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun seedForDate(date: LocalDate = LocalDate.now()): Long {
        val input = date.format(DATE_FORMAT)
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(input.toByteArray(Charsets.UTF_8))
        // Use first 4 bytes as a long (little-endian)
        return (digest[0].toLong() and 0xFF) or
               ((digest[1].toLong() and 0xFF) shl 8) or
               ((digest[2].toLong() and 0xFF) shl 16) or
               ((digest[3].toLong() and 0xFF) shl 24)
    }

    /** Board size derived deterministically from seed */
    fun boardSizeForSeed(seed: Long): Int {
        // 3×3, 5×5, 7×7 based on seed parity / range
        return when ((seed % 3).toInt()) {
            0 -> 3
            1 -> 5
            else -> 7
        }
    }
}
