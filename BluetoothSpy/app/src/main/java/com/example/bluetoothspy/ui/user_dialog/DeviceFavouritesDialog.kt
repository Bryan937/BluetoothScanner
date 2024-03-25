package com.example.bluetoothspy.ui.user_dialog

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.bluetoothspy.constants.SplashScreenConstants
import com.example.bluetoothspy.models.Device
import com.example.bluetoothspy.ui.components.DeviceComponent
import com.example.bluetoothspy.ui.components.TextComponent
import com.example.bluetoothspy.ui.theme.BluetoothSpyTheme

@Composable
fun ShowDeviceFavourites(devices: List<Device>, onDialogDismissed: () -> Unit) {
    Box(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center)
    {
        DeviceFavourites(devices, onDialogDismissed)
    }
}
@Composable
fun DeviceFavourites(devices: List<Device>, onDialogDismissed: () -> Unit) {
    val gradientColors = listOf(SplashScreenConstants.LOGO_COLOR, MaterialTheme.colorScheme.onSurface)
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = true },
            properties = DialogProperties(
                dismissOnClickOutside = false,
                dismissOnBackPress = false
            )
        ) {
            Surface(modifier = Modifier
                .wrapContentSize()
                .height(600.dp)) {
                Box(
                    contentAlignment = Alignment.TopCenter // Align content to the top
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(start = 15.dp, end = 15.dp),
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Favorites",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onSurface,
                                style = TextStyle(
                                    brush = Brush.linearGradient(
                                        colors = gradientColors
                                    )
                                ),
                                textAlign = TextAlign.Center,
                            )
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.CenterEnd
                            )
                            {
                                Button(onClick = {
                                    showDialog = false
                                    onDialogDismissed()
                                })
                                {
                                    TextComponent(
                                        textValue = "Back", textSize = 14.sp,
                                        colorValue = MaterialTheme.colorScheme.onPrimary,
                                        fontWeightValue = FontWeight.Bold
                                    )
                                }
                            }
                        }
                        DevicesBox(devices = devices)
                    }
                }
            }
        }
    }
}
@Composable
fun DevicesBox(devices: List<Device>) {
    Box(modifier = Modifier.wrapContentSize())
    {
        LazyColumn(
            modifier = Modifier
                .padding(vertical = 5.dp)
                .wrapContentSize()
        ) {
            items(items = devices) { device ->
                DeviceComponent(device = device)
            }
        }
    }
}