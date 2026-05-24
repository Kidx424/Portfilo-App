package com.portfolio.jobmarket.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = NeonPurple,
    onPrimary = Color.White,
    secondary = NeonPink,
    onSecondary = Color.White,
    tertiary = NeonBlue,
    onTertiary = Color.White,
    background = Neutral50,
    onBackground = Neutral900,
    surface = Color.White,
    onSurface = Neutral900,
    surfaceVariant = Neutral100,
    onSurfaceVariant = Neutral500,
)

private val DarkColors = darkColorScheme(
    primary = NeonPurple,
    onPrimary = Color.White,
    secondary = NeonPink,
    onSecondary = Color.White,
    tertiary = NeonBlue,
    onTertiary = Color.White,
    background = Black,
    onBackground = Neutral50,
    surface = Neutral900,
    onSurface = Neutral50,
    surfaceVariant = Neutral800,
    onSurfaceVariant = Neutral500,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = AppTypography,
        content = content
    )
}
