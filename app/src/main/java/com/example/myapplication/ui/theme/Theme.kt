package com.example.myapplication.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = GreenOnPrimary,
    secondary = GreenSecondary,
    background = GreenBackground,
    surface = GreenSurface,
    onSurface = GreenOnSurface
)

@Composable
fun MyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography(), // use a tipografia padrão ou crie uma customizada
        shapes = Shapes(),
        content = content
    )
}
