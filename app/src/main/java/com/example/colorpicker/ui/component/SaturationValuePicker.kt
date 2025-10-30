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

@Composable
fun SaturationValuePicker(
    modifier: Modifier = Modifier,
    hue: Float,
    saturation: Float,
    value: Float,
    onColorChanged: (saturation: Float, value: Float) -> Unit
) {
    val baseColor = remember(hue) {
        Color.hsv(hue, 1f, 1f)
    }

    Canvas(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    change.consume()
                    val x = change.position.x.coerceIn(0f, size.width.toFloat())
                    val y = change.position.y.coerceIn(0f, size.height.toFloat())

                    val newSaturation = (x / size.width).coerceIn(0f, 1f)
                    val newValue = (1f - (y / size.height)).coerceIn(0f, 1f)

                    onColorChanged(newSaturation, newValue)
                }
            }
    ) {
        // Saturation Gradient
        drawRect(
            brush = Brush.horizontalGradient(
                colors = listOf(Color.White, baseColor)
            ),
            size = size
        )

        // Brightness Gradient
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color.Black)
            ),
            size = size
        )

        // Current Selected Position
        val x = saturation * size.width
        val y = (1f - value) * size.height
        drawCircle(
            color = Color.White,
            radius = 12.dp.toPx(),
            center = Offset(x, y),
            style = Stroke(width = 2.dp.toPx())
        )
        drawCircle(
            color = Color.Black,
            radius = 12.dp.toPx(),
            center = Offset(x, y),
            style = Stroke(width = 1.dp.toPx())
        )
    }
}