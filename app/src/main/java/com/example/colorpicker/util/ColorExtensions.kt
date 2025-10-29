package com.example.colorpicker.util

import androidx.compose.ui.graphics.Color

fun Color.toHexString(): String {
    val red = (this.red * 255).toInt()
    val green = (this.green * 255).toInt()
    val blue = (this.blue * 255).toInt()
    return "%02X%02X%02X".format(red, green, blue)
}