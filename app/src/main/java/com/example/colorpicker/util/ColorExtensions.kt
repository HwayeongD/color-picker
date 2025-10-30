package com.example.colorpicker.util

import androidx.compose.ui.graphics.Color
import com.example.colorpicker.model.CmykColor
import com.example.colorpicker.model.HsvColor
import com.example.colorpicker.model.LabColor
import com.example.colorpicker.model.RgbColor
import kotlin.math.pow
import kotlin.math.roundToInt

/**
 * Color를 Hex로 변환
 * @return "RRGGBB" 형식의 문자열 (# 제외)
 */
fun Color.toHexString(): String {
    val red = (this.red * 255).toInt()
    val green = (this.green * 255).toInt()
    val blue = (this.blue * 255).toInt()
    return "%02X%02X%02X".format(red, green, blue)
}

/**
 * Color를 RGB 정수값으로 변환
 */
fun Color.toRgb(): RgbColor {
    return RgbColor(
        r = (this.red * 255).roundToInt(),
        g = (this.green * 255).roundToInt(),
        b = (this.blue * 255).roundToInt()
    )
}

/**
 * Color를 CMYK로 변환
 */
fun Color.toCMYK(): CmykColor {
    val r = this.red
    val g = this.green
    val b = this.blue

    if(r == 0f && g == 0f && b == 0f) {
        return CmykColor(0, 0, 0, 100)
    }

    val k = 1f - maxOf(r, g, b)
    val c = (1f - r - k) / (1f - k)
    val m = (1f - g - k) / (1f - k)
    val y = (1f - b - k) / (1f - k)

    return CmykColor(
        c = (c * 100).roundToInt(),
        m = (m * 100).roundToInt(),
        y = (y * 100).roundToInt(),
        k = (k * 100).roundToInt()
    )
}

/**
 * Color를 CIE Lab를 변환
 */
fun Color.toLab(): LabColor {
    var r = this.red
    var g = this.green
    var b = this.blue

    r = if (r > 0.04045f) ((r + 0.055f) / 1.055f).pow(2.4f) else r / 12.92f
    g = if (g > 0.04045f) ((g + 0.055f) / 1.055f).pow(2.4f) else g / 12.92f
    b = if (b > 0.04045f) ((b + 0.055f) / 1.055f).pow(2.4f) else b / 12.92f

    var x = r * 0.4124564f + g * 0.3575761f + b * 0.1804375f
    var y = r * 0.2126729f + g * 0.7151522f + b * 0.0721750f
    var z = r * 0.0193339f + g * 0.1191920f + b * 0.9503041f

    x /= 0.95047f
    y /= 1.00000f
    z /= 1.08883f

    x = if (x > 0.008856f) x.pow(1f / 3f) else (7.787f * x + 16f / 116f)
    y = if (y > 0.008856f) y.pow(1f / 3f) else (7.787f * y + 16f / 116f)
    z = if (z > 0.008856f) z.pow(1f / 3f) else (7.787f * z + 16f / 116f)

    val l = (116f * y - 16f)
    val a = 500f * (x - y)
    val bValue = 200f * (y - z)

    return LabColor(
        l = l.roundToInt().coerceIn(0, 100),
        a = a.roundToInt().coerceIn(-128, 127),
        b = bValue.roundToInt().coerceIn(-128, 127)
    )
}

/**
 * Hex를 Color로 변환
 * @param hex "#RRGGBB" 또는 "RRGGBB" 형식
 * @return Color 객체, 유효하지 않으면 null
 */
fun String.toColorOrNull(): Color? {
    return try {
        val cleanHex = this.removePrefix("#").trim()

        if(cleanHex.length != 6) return null

        val r = cleanHex.substring(0, 2).toInt(16)
        val g = cleanHex.substring(2, 4).toInt(16)
        val b = cleanHex.substring(4, 6).toInt(16)

        Color(r / 255f, g / 255f, b / 255f)
    } catch (e: Exception) {
        null
    }
}

/**
 * Color를 HSV로 변환
 */
fun Color.toHsv(): HsvColor {
    val r = this.red
    val g = this.green
    val b = this.blue

    val max = maxOf(r, g, b)
    val min = minOf(r, g, b)
    val delta = max - min

    // Hue 계산
    val hue = when {
        delta == 0f -> 0f
        max == r -> 60f * (((g - b) / delta) % 6)
        max == g -> 60f * (((b - r) / delta) + 2)
        else -> 60f * (((r - g) / delta) + 4)
    }.let { if(it < 0) it + 360f else it }

    // Saturation 계산
    val saturation = if (max == 0f) 0f else delta / max

    // Value 계산
    val value = max

    return HsvColor(hue, saturation, value)
}