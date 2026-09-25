package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SensiFireDarkColorScheme = darkColorScheme(
    primary = FireOrange,
    onPrimary = Color.White,
    primaryContainer = FireOrangeDark,
    onPrimaryContainer = Color(0xFFFFDBCF),
    secondary = FireCrimson,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF450A0A),
    onSecondaryContainer = Color(0xFFFFCDD2),
    tertiary = FireGold,
    onTertiary = Color.Black,
    background = DarkObsidian,
    onBackground = TextPrimary,
    surface = DarkSurface,
    onSurface = TextPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = DarkBorder,
    outlineVariant = DarkBorderGlowing
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SensiFireDarkColorScheme,
        typography = Typography,
        content = content
    )
}
