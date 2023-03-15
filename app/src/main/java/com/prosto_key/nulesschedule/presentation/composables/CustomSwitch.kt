package com.prosto_key.nulesschedule.presentation.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomSwitch(
    modifier: Modifier = Modifier,
    switchState: Boolean,
    onStateChange: () -> Unit = {},
    scale: Float = 1.2f,
    width: Dp = 30.dp,
    height: Dp = 18.dp,
    checkedTrackColor: Color = MaterialTheme.colors.secondary,
    uncheckedTrackColor: Color = MaterialTheme.colors.onBackground,
    checkedThumbColor: Color = MaterialTheme.colors.primaryVariant,
    uncheckedThumbColor: Color = MaterialTheme.colors.secondary,
    gapBetweenThumbAndTrackEdge: Dp = 3.dp
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val thumbRadius = (height / 2) - gapBetweenThumbAndTrackEdge

        // To move the thumb, we need to calculate the position (along x axis)
        val animatePosition = animateFloatAsState(
            targetValue = if (switchState)
                with(LocalDensity.current) { (width - thumbRadius - gapBetweenThumbAndTrackEdge).toPx() }
            else
                with(LocalDensity.current) { (thumbRadius + gapBetweenThumbAndTrackEdge).toPx() }
        )

        Canvas(
            modifier = Modifier
                .size(width = width, height = height)
                .scale(scale = scale)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            // This is called when the user taps on the canvas
                            onStateChange()
                        }
                    )
                }
        ) {
            // Track
            drawRoundRect(
                color = if (switchState) checkedTrackColor else uncheckedTrackColor,
                cornerRadius = CornerRadius(x = 10.dp.toPx(), y = 10.dp.toPx())
            )

            // Thumb
            drawCircle(
                color = if (switchState) checkedThumbColor else uncheckedThumbColor,
                radius = thumbRadius.toPx(),
                center = Offset(
                    x = animatePosition.value,
                    y = size.height / 2
                )
            )
        }
    }
}