package com.example.bluetoothspy.ui.main_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bluetoothspy.models.Device
import com.example.bluetoothspy.ui.components.DeviceComponent
import com.example.bluetoothspy.viewModels.DeviceViewModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun LeftSection(deviceList: StateFlow<List<Device>>) {
    val deviceState = deviceList.collectAsState()

    Column(modifier = Modifier.fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
        // Devices  section space 3f
        Box(modifier = Modifier.weight(3f))
        {
            LazyColumn(modifier = Modifier
                .padding(vertical = 5.dp)
                .wrapContentSize()) {
                items(items = deviceState.value) { device ->
                    DeviceComponent(device = device)
                }
            }
        }
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd,)
        {
            ButtonsSection()
        }


    }
}
@Preview(
    showSystemUi = true,
    device = "spec:width=1360dp,height=900dp,dpi=287,isRound=false,chinSize=0dp,orientation=landscape"
)@Composable
fun LeftPreview() {
    // Le preview fonctionne pas car le composable prenne des State en paramètre
    // qui sont seulement convertie en liste en runtime
    val devicePreview = DeviceViewModel()
    val previewList = listOf(
        Device("Samsung S21", "00:60:AD", "2", "1"),
        Device("Samsung A1", "00:35:TY", "2", "3")
    )
    devicePreview.updateDeviceList(previewList)
    LeftSection(devicePreview.deviceListState)
}