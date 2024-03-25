package com.example.bluetoothspy.viewModels

import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.lifecycle.ViewModel
import com.example.bluetoothspy.models.Device
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class DeviceViewModel : ViewModel() {
    private val _deviceListState = MutableStateFlow(emptyList<Device>())
    val deviceListState: StateFlow<List<Device>> = _deviceListState
    var latitude: MutableDoubleState = mutableDoubleStateOf(0.0)
    var longitude: MutableDoubleState = mutableDoubleStateOf(0.0)



    fun updateDeviceList(newDeviceList: List<Device>) {
        _deviceListState.value = newDeviceList
    }

    fun setPosition(lat: Double, long: Double) {
        latitude.doubleValue = lat
        longitude.doubleValue = long
    }
}



