package com.example.bluetoothspy.ui.splash_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bluetoothspy.constants.CommonConstants.SPACER_SIZE
import com.example.bluetoothspy.constants.SplashScreenConstants.BY_SIZE
import com.example.bluetoothspy.constants.SplashScreenConstants.BY_STRING
import com.example.bluetoothspy.constants.SplashScreenConstants.DEV_MEMBER_SIZE
import com.example.bluetoothspy.constants.SplashScreenConstants.DEV_TEAM
import com.example.bluetoothspy.ui.components.TextComponent

@Composable
fun TeamMemberList() {
    Surface(
        modifier = Modifier.wrapContentSize()
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextComponent(
                textValue = BY_STRING,
                textSize = BY_SIZE,
                colorValue = MaterialTheme.colorScheme.onBackground,
                fontWeightValue = FontWeight.W400,
                fontStyleValue = FontStyle.Italic
            )
            Spacer(modifier = Modifier.size(SPACER_SIZE))
            DEV_TEAM.forEach { devMember ->
                TextComponent(
                    textValue = devMember,
                    textSize = DEV_MEMBER_SIZE,
                    colorValue = MaterialTheme.colorScheme.onSurface,
                    fontWeightValue = FontWeight.Bold

                )
            }
        }
    }

}

@Preview
@Composable
fun TeamMembersPreview() {
    TeamMemberList()
}