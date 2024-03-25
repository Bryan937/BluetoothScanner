package com.example.bluetoothspy.ui.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.bluetoothspy.constants.CommonConstants.SMALL_PADDING
import com.example.bluetoothspy.models.Device
import com.example.bluetoothspy.models.User
import com.example.bluetoothspy.viewModels.DeviceViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapSection(deviceViewModel: DeviceViewModel) {
    var devices by remember { mutableStateOf<List<Device>>(emptyList()) }
    var isDataLoaded by remember { mutableStateOf(false) }
    var zoomLevel by remember { mutableFloatStateOf(13f) }
    val currentLatitude = deviceViewModel.latitude.doubleValue

    val currentLocation =
        LatLng(deviceViewModel.latitude.doubleValue, deviceViewModel.longitude.doubleValue)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, zoomLevel)
    }
    User.instance.retrieveFromDatabase { fetchedData ->
        devices = fetchedData
        isDataLoaded = true
    }

    LaunchedEffect(devices, currentLocation) {
        cameraPositionState.position =
            CameraPosition.Builder().target(currentLocation).zoom(zoomLevel).build()
    }


    if (isDataLoaded) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(SMALL_PADDING)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                zoomLevel = cameraPositionState.position.zoom
                if (currentLatitude.toFloat() != 0f) {
                    Marker(
                        state = MarkerState(position = currentLocation),
                        title = "Current location",
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)
                    )
                }

                devices.forEach { device ->
                    val latLng = LatLng(device.latitude, device.longitude)
                    val usedPin = if (device.isFavourite) {
                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
                    } else {
                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                    }
                    MarkerInfoWindow(
                        state = MarkerState(position = latLng),
                        title = device.name,
                        icon = usedPin
                    ) { marker ->
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .background(color = Color.White)
                        ) {
                            Column {
                                Text(
                                    text = marker.snippet ?: device.infoForMap(),
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
