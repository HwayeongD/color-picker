package com.example.colorpicker.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Main Top Bar
 */
@Composable
fun ColorPickerTopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF313131))
            .padding(32.dp)
    ) {
        Text(
            text = "Color Picker",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )
    }
}