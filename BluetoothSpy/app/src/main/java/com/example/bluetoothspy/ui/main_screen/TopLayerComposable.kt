package com.example.bluetoothspy.ui.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.bluetoothspy.R
import com.example.bluetoothspy.constants.CommonConstants
import com.example.bluetoothspy.constants.CommonConstants.LARGE_PADDING
import com.example.bluetoothspy.constants.SplashScreenConstants


@Composable
fun TopLayer() {
    val gradientColors = listOf(SplashScreenConstants.LOGO_COLOR, MaterialTheme.colorScheme.onSurface)
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = LARGE_PADDING, end = LARGE_PADDING),
        verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier
                    .padding(CommonConstants.SMALL_PADDING)
                    .size(SplashScreenConstants.SMALL_LOGO_SIZE),
                painter = painterResource(id = R.drawable.ic_launcher),
                contentDescription = SplashScreenConstants.LOGO_IMAGE_DESCRIPTION
            )
            Text(
                text = SplashScreenConstants.APP_NAME,
                fontSize = CommonConstants.NORMAL_FONT_SIZE,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = gradientColors
                    )
                )
            )
        }


}

@Preview
@Composable
fun TopLayerPreview() {
    TopLayer()
}