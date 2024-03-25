package com.example.bluetoothspy.ui.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bluetoothspy.R
import com.example.bluetoothspy.constants.CommonConstants.MEDIUM_FONT_SIZE
import com.example.bluetoothspy.constants.CommonConstants.SMALL_PADDING
import com.example.bluetoothspy.constants.SplashScreenConstants.APP_NAME
import com.example.bluetoothspy.constants.SplashScreenConstants.LOGO_IMAGE_DESCRIPTION
import com.example.bluetoothspy.constants.SplashScreenConstants.LOGO_COLOR
import com.example.bluetoothspy.constants.SplashScreenConstants.LOGO_SIZE


@Composable
fun SplashScreenLogo() {
    val gradientColors = listOf(LOGO_COLOR, MaterialTheme.colorScheme.onSurface)
    Surface(
        modifier = Modifier.wrapContentSize()
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .padding(SMALL_PADDING)
                    .size(LOGO_SIZE),
                painter = painterResource(id = R.drawable.ic_launcher),
                contentDescription = LOGO_IMAGE_DESCRIPTION
            )
            Text(
                text = APP_NAME,
                fontSize = MEDIUM_FONT_SIZE,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface,
                style = androidx.compose.ui.text.TextStyle(
                    brush = Brush.linearGradient(
                        colors = gradientColors
                    )
                )
            )
        }
    }


}

@Preview
@Composable
fun LogoPreview() {
    SplashScreenLogo()
}
