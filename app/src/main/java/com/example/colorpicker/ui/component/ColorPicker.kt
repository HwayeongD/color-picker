package com.example.colorpicker.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

/**
 * Color Picker
 */
@Composable
fun ColorPicker(
    pickedColor: Color,
    onHexCodeChanged: (String) -> Unit,
    onColorChanged: (Color) -> Unit
) {
    val controller = rememberColorPickerController()

    LaunchedEffect(pickedColor) {
        controller.selectByColor(pickedColor, true)
    }
}