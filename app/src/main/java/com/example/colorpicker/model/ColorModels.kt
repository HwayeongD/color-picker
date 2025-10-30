package com.example.colorpicker.model

/**
 * RGB 색상 모델
 * @param r Red (0-255)
 * @param g Green (0-255)
 * @param b Blue (0-255)
 */
data class RgbColor(
    val r: Int,
    val g: Int,
    val b: Int
)

/**
 * CMYK 색상 모델
 * @param c Cyan (0-100%)
 * @param m Magenta (0-100%)
 * @param y Yellow (0-100%)
 * @param k Key/Black (0-100%)
 */
data class CmykColor(
    val c: Int,
    val m: Int,
    val y: Int,
    val k: Int
)

/**
 * CIE Lab 색상 모델
 * @param l Lightness (0-100)
 * @param a Green-Red axis (-128 to 127)
 * @param b Blue-Yellow axis (-128 to 127)
 */
data class LabColor(
    val l: Int,
    val a: Int,
    val b: Int
)