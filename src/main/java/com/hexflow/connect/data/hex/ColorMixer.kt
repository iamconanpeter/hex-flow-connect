package com.hexflow.connect.data.hex

import com.hexflow.connect.domain.hex.HexCoordinate

/**
 * Handles color mixing rules for the Hex Flow Connect game
 */
object ColorMixer {
    /**
     * Primary colors used in the game
     */
    enum class PrimaryColor {
        RED, GREEN, BLUE, YELLOW, CYAN, MAGENTA
    }

    /**
     * Mixes two primary colors together
     * @return the resulting color or null if colors cannot be mixed
     */
    fun mixColors(color1: PrimaryColor, color2: PrimaryColor): PrimaryColor? {
        return when {
            color1 == color2 -> color1 // Same color -> no change
            (color1 == PrimaryColor.RED && color2 == PrimaryColor.YELLOW) ||
            (color1 == PrimaryColor.YELLOW && color2 == PrimaryColor.RED) -> PrimaryColor.ORANGE
            (color1 == PrimaryColor.GREEN && color2 == PrimaryColor.BLUE) ||
            (color1 == PrimaryColor.BLUE && color2 == PrimaryColor.GREEN) -> PrimaryColor.CYAN
            (color1 == PrimaryColor.BLUE && color2 == PrimaryColor.MAGENTA) ||
            (color1 == PrimaryColor.MAGENTA && color2 == PrimaryColor.BLUE) -> PrimaryColor.PURPLE
            else -> null
        }
    }

    /**
     * Determines if two colors are a match for the puzzle
     */
    fun isColorMatch(color1: PrimaryColor, color2: PrimaryColor): Boolean {
        // Same colors match
        if (color1 == color2) return true
        // Complementary colors also match (e.g., RED and BLUE)
        return when {
            (color1 == PrimaryColor.RED && color2 == PrimaryColor.BLUE) ||
            (color1 == PrimaryColor.BLUE && color2 == PrimaryColor.RED) -> true
            (color1 == PrimaryColor.GREEN && color2 == PrimaryColor.MAGENTA) ||
            (color1 == PrimaryColor.MAGENTA && color2 == PrimaryColor.GREEN) -> true
            (color1 == PrimaryColor.YELLOW && color2 == PrimaryColor.CYAN) ||
            (color1 == PrimaryColor.CYAN && color2 == PrimaryColor.YELLOW) -> true
            else -> false
        }
    }

    /**
     * Creates a visual color palette for the game
     */
    fun createColorPalette(): List<PrimaryColor> {
        return listOf(
            PrimaryColor.RED,
            PrimaryColor.GREEN,
            PrimaryColor.BLUE,
            PrimaryColor.YELLOW,
            PrimaryColor.CYAN,
            PrimaryColor.MAGENTA
        )
    }
}