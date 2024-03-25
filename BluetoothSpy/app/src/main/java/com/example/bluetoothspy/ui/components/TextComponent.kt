package com.example.bluetoothspy.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit

@Composable
fun TextComponent(textValue: String,
                  textSize: TextUnit,
                  colorValue: Color = MaterialTheme.colorScheme.onBackground,
                  fontWeightValue: FontWeight = FontWeight.Light,
                  fontStyleValue: FontStyle = FontStyle.Normal){
    Text(
        text = textValue,
        fontSize = textSize,
        color = colorValue,
        fontWeight = fontWeightValue,
        fontStyle = fontStyleValue,
    )
}