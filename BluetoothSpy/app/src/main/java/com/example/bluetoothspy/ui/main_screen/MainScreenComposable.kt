package com.example.bluetoothspy.ui.main_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bluetoothspy.constants.CommonConstants.LARGE_PADDING
import com.example.bluetoothspy.constants.CommonConstants.SPACER_SIZE
import com.example.bluetoothspy.models.Device
import com.example.bluetoothspy.models.User
import com.example.bluetoothspy.viewModels.DeviceViewModel

@Composable
fun MainScreen(devicesViewModel: DeviceViewModel) {
    var devices by remember { mutableStateOf<List<Device>>(emptyList()) }
    var isDataLoaded by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier.fillMaxSize().padding(bottom = 40.dp)
    ) {
        Column( modifier = Modifier
            .fillMaxSize()
            .padding(LARGE_PADDING)) {
            TopLayer()
            Spacer(modifier = Modifier.padding(SPACER_SIZE))

            Row(modifier = Modifier.fillMaxWidth()) {
                // Map section
                Box(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight()
                ) {
                    MapSection(devicesViewModel)
                }

                // Left section
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .wrapContentWidth()
                ) {
                    LeftSection(devicesViewModel.deviceListState)
                }
            }
        }
    }
}