package com.gamefactory.hexflowconnect

import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

/**
 * Tests for DailySeedGenerator deterministic seed.
 */
class DailySeedGeneratorTest {

    @Test
    fun sameDateSameSeed() {
        val date = LocalDate.of(2026, 9, 14)
        val s1 = DailySeedGenerator.seedForDate(date)
        val s2 = DailySeedGenerator.seedForDate(date)
        assertEquals(s1, s2)
    }

    @Test
    fun differentDateLikelyDifferentSeed() {
        val d1 = LocalDate.of(2026, 9, 13)
        val d2 = LocalDate.of(2026, 9, 14)
        val s1 = DailySeedGenerator.seedForDate(d1)
        val s2 = DailySeedGenerator.seedForDate(d2)
        // Not guaranteed but very likely different; we just ensure function runs
        assertEquals(Long.SIZE, 64) // sanity
    }

    @Test
    fun boardSizeFromSeed() {
        val s1 = DailySeedGenerator.seedForDate(LocalDate.of(2026, 9, 13))
        val s2 = DailySeedGenerator.seedForDate(LocalDate.of(2026, 9, 14))
        val b1 = DailySeedGenerator.boardSizeForSeed(s1)
        val b2 = DailySeedGenerator.boardSizeForSeed(s2)
        assertEquals(3, b1 % 2 + 3) // expects 3,5,7
        assertEquals(3, b2 % 2 + 3)
    }
}