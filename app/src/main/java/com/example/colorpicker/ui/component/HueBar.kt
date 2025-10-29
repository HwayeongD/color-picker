package com.example.colorpicker.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

/**
 * Color Picker
 */
@Composable
fun HueBar(
    modifier: Modifier = Modifier,
    hue: Float,
    onHueChanged: (Float) -> Unit
) {
    val hueColors = remember {
        listOf(
            Color.Red,
            Color.Magenta,
            Color.Blue,
            Color.Cyan,
            Color.Green,
            Color.Yellow,
            Color.Red
        )
    }

    Canvas(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    change.consume()
                    val y = change.position.y.coerceIn(0f, size.height.toFloat())
                    val newHue = ((size.height - y) / size.height) * 360f
                    onHueChanged(newHue.coerceIn(0f, 360f))
                }
            }
    ) {
        // Draw Hue Gradient
        drawRect(
            brush = Brush.verticalGradient(hueColors),
            size = size
        )

        // Current Selected Position
        val y = size.height - ((hue / 360f) * size.height)
        drawCircle(
            color = Color.White,
            radius = 12.dp.toPx(),
            center = Offset(size.width / 2, y),
            style = Stroke(width = 2.dp.toPx())
        )
    }
}