package com.example.colorpicker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.colorpicker.ui.component.ColorInfoSection
import com.example.colorpicker.ui.component.HueBar
import com.example.colorpicker.ui.component.SaturationValuePicker
import com.example.colorpicker.util.toColorOrNull
import com.example.colorpicker.util.toHexString
import com.example.colorpicker.util.toHsv
import java.util.regex.Pattern

@Composable
fun ColorPickerScreen(
    modifier: Modifier = Modifier
) {
    val regex = "^[0-9a-fA-F]*$" // HEX 정규표현식
    var hexInput by remember { mutableStateOf("") } // Hex 입력값

    var hue by remember { mutableFloatStateOf(0f) } // 0 ~ 360
    var saturation by remember { mutableFloatStateOf(1f) } // 0 ~ 1
    var value by remember { mutableFloatStateOf(1f) } // 0 ~ 1

    val selectedColor = remember(hue, saturation, value) {
        Color.hsv(hue, saturation, value)
    }

    LaunchedEffect(selectedColor) {
        hexInput = selectedColor.toHexString()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(160.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Color Picker
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // SV Picker
            SaturationValuePicker(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                hue = hue,
                saturation = saturation,
                value = value,
                onColorChanged = { s, v ->
                    saturation = s
                    value = v
                }
            )

            // Hue Bar
            HueBar(
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight(),
                hue = hue,
                onHueChanged = { hue = it }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Preview - Selected Color
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(selectedColor)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Color Code 입력
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = hexInput,
                onValueChange = { newHex ->
                    if(newHex.length > 6) return@OutlinedTextField // 글자수 6자 제한
                    if(!Pattern.matches(regex, newHex)) return@OutlinedTextField // 정규표현식 검증

                    hexInput = newHex // 입력값 갱신
                },
                modifier = Modifier.width(200.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                singleLine = true,
                label = { Text("Hex Color") },
                prefix = { Text("#") },
                colors = TextFieldDefaults.colors().copy(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            // Button
            Text(
                text = "조회",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable {
                        if(hexInput.length != 6) return@clickable // 글자수 6자 제한
                        if(!Pattern.matches(regex, hexInput)) return@clickable // 정규표현식 검증

                        hexInput.toColorOrNull()?.let { color ->
                            val hsv = color.toHsv()
                            hue = hsv.h
                            saturation = hsv.s
                            value = hsv.v
                        }
                    }
                    .padding(
                        vertical = 12.dp,
                        horizontal = 24.dp
                    )
            )
        }

        // Color Info
        ColorInfoSection(selectedColor, hue, saturation, value)
    }
}