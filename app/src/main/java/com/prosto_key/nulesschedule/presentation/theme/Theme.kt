package com.prosto_key.nulesschedule.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = green_400,
    primaryVariant = green_100,
    secondary = green_800,
    background = green_50,
    surface = gray_100,
    onPrimary = Color.White,
    onSecondary = green_70,
    onBackground = green_200,
    onSurface = gray_500,
)

@Composable
fun NulesScheduleTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}