package com.example.colorpicker.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.colorpicker.util.toCMYK
import com.example.colorpicker.util.toHexString
import com.example.colorpicker.util.toLab
import com.example.colorpicker.util.toRgb
import kotlin.math.roundToInt

@Composable
fun ColorInfoSection(
    color: Color,
    hue: Float,
    saturation: Float,
    value: Float
) {
    val rgb = color.toRgb()
    val cmyk = color.toCMYK()
    val lab = color.toLab()

    Column(
        modifier = Modifier.padding(top = 32.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalDivider()

        Row(
            horizontalArrangement = Arrangement.spacedBy(200.dp)
        ) {
            ColorModelGroup(
                title = "HSV",
                values = listOf(
                    "H" to "${hue.roundToInt()}°",
                    "S" to "${(saturation * 100).roundToInt()}%",
                    "V" to "${(value * 100).roundToInt()}%"
                )
            )

            ColorModelGroup(
                title = "CIE Lab",
                values = listOf(
                    "L" to "${lab.l}",
                    "a" to "${lab.a}",
                    "b" to "${lab.b}"
                )
            )
        }

        HorizontalDivider()

        Row(
            horizontalArrangement = Arrangement.spacedBy(200.dp)
        ) {
            ColorModelGroup(
                title = "RGB",
                values = listOf(
                    "R" to "${rgb.r}",
                    "G" to "${rgb.g}",
                    "B" to "${rgb.b}"
                )
            )

            ColorModelGroup(
                title = "CMYK",
                values = listOf(
                    "C" to "${cmyk.c}%",
                    "M" to "${cmyk.m}%",
                    "Y" to "${cmyk.y}%",
                    "K" to "${cmyk.k}%"
                )
            )
        }
    }
}

@Composable
fun ColorModelGroup(
    title: String,
    values: List<Pair<String, String>>
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            values.forEach { (label, value) ->
                Row {
                    Text(
                        text = "$label: ",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = value,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}