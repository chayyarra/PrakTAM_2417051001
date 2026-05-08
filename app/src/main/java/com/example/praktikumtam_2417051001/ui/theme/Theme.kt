package com.example.praktikumtam_2417051001.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = CoralPrimary,
    onPrimary = WhitePure,
    primaryContainer = PeachAccent,
    onPrimaryContainer = TextDark,
    secondary = PeachAccent,
    onSecondary = TextDark,
    background = CreamBackground,
    onBackground = TextDark,
    surface = WhitePure,
    onSurface = TextDark,
    error = Color(0xFFD32F2F)
)

@Composable
fun PraktikumTAM_2417051001Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = AppTypography,
        content = content
    )
}
