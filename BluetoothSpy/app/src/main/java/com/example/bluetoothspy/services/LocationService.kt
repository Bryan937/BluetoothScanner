package com.example.bluetoothspy.services

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.bluetoothspy.models.Device
import com.example.bluetoothspy.viewModels.DeviceViewModel
import com.google.android.gms.location.LocationServices
import kotlin.math.cos

class LocationService(private val activity: Activity,
                      private val btService: BluetoothService,
                      private val deviceViewModel: DeviceViewModel)
{
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    fun getDeviceCoordinates() {
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    deviceViewModel.setPosition(location.latitude, location.longitude)
                    btService.setCoordinates(location.latitude, location.longitude)
                    btService.startDiscovery()
                    Log.d("BryanLocation", "location set: $location")
                }
            }
        }
    }
}