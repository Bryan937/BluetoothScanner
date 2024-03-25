package com.example.bluetoothspy.constants

import android.os.Build

object BluetoothPermissionConstants {
    const val BLUETOOTH_SCAN_MIN_SDK = Build.VERSION_CODES.S
    const val BACKGROUND_LOC_MIN_SDK = Build.VERSION_CODES.Q
    const val BLUETOOTH_CONNECT_MIN_SDK = Build.VERSION_CODES.S
    const val ACCESS_LOCATION_MIN_SDK = Build.VERSION_CODES.P
}