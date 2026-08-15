package com.hexflow.connect.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE),
    secondary = Color(0xFF03DAC5),
    surface = Color.White,
    surfaceVariant = Color(0xFFFAFAFA),
    tertiary = Color(0xFF64B5F6)
)

private val LightShadeScheme = lightColorScheme(
    background = Color(0xFFFAFAFA),
    surface = Color.White,
    secondary = Color(0xFF03DAC5),
    primary = Color(0xFF6200EE),
    error = Color(0xFFCF6679),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onSurface = Color.Black,
    onSurfaceVariant = Color(0xFF66BB6A),
    tertiary = Color(0xFF90CAF9)
)

@Composable
fun HexFlowConnectTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        shadeScheme = LightShadeScheme,
        content = content
    )
}