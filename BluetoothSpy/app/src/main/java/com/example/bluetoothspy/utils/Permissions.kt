package com.example.bluetoothspy.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.isTraceInProgress
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.bluetoothspy.constants.BluetoothPermissionConstants
import com.example.bluetoothspy.services.BluetoothService
import com.example.bluetoothspy.services.LocationService

class Permissions (private val btService: BluetoothService,
                   private val locService: LocationService,
                   private val activity: ComponentActivity)
{
    private fun isPermissionGranted(activity: Activity, permission: String, minSdk: Int? = null): Boolean {
        var isGranted = true
        if (minSdk == null || Build.VERSION.SDK_INT >= minSdk) {
            isGranted = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
        }
        return isGranted
    }

    private fun requestMultiplePermissions(permissions: List<String>) {
        val requestPermissionLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionsMap ->
            for ((permission, isGranted) in permissionsMap) {
                val isLocationPermission = permission == Manifest.permission.ACCESS_FINE_LOCATION || permission == Manifest.permission.ACCESS_COARSE_LOCATION
                if (isLocationPermission && isGranted) {
                    locService.getDeviceCoordinates()
                }
                if (isGranted) {
                    btService.startDiscovery()
                }
            }
        }
        requestPermissionLauncher.launch(permissions.toTypedArray())
    }

    fun checkInitialPermissions() {
        val locationPermission = if (Build.VERSION.SDK_INT > BluetoothPermissionConstants.ACCESS_LOCATION_MIN_SDK) {
            Manifest.permission.ACCESS_FINE_LOCATION
        } else {
            Manifest.permission.ACCESS_COARSE_LOCATION
        }

        val permissionRequested = mutableSetOf(
            Pair(Manifest.permission.ACCESS_BACKGROUND_LOCATION, BluetoothPermissionConstants.BACKGROUND_LOC_MIN_SDK),
            Pair(locationPermission, null),
            Pair(Manifest.permission.BLUETOOTH_CONNECT, BluetoothPermissionConstants.BLUETOOTH_CONNECT_MIN_SDK),
            Pair(Manifest.permission.BLUETOOTH_SCAN, BluetoothPermissionConstants.BLUETOOTH_SCAN_MIN_SDK)
        )

        val permissionsRequestToKeep = mutableSetOf<String>()

        for ((permission, minSdk) in permissionRequested) {
            if (!isPermissionGranted(activity, permission, minSdk)) {
                permissionsRequestToKeep.add(permission)
            }
        }
        requestMultiplePermissions(permissionsRequestToKeep.toList())
    }
}
