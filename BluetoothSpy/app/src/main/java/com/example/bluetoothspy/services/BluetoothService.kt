package com.example.bluetoothspy.services

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.bluetoothspy.constants.BluetoothPermissionConstants
import com.example.bluetoothspy.models.Device
import com.example.bluetoothspy.models.User
import com.example.bluetoothspy.utils.Permissions
import com.example.bluetoothspy.viewModels.DeviceViewModel
import kotlin.math.cos

class BluetoothService (private val activity: ComponentActivity,
                        private val devicesViewModel: DeviceViewModel,
) {
    var deviceList = devicesViewModel.deviceListState
    private val btAdapter = getBluetoothAdapter()
    private var deviceLatitude: Double = 0.0
    private var deviceLongitude: Double = 0.0
    val r_earth = 6378.0

    // getBluetoothAdapter() is a private function that retrieves the Bluetooth adapter from the system service
    private fun getBluetoothAdapter(): BluetoothAdapter? {
        val bluetoothManager = activity.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        return bluetoothManager.adapter
    }

    // enableBluetooth() is a function that checks if Bluetooth is supported on the device and if it is enabled
    fun enableBluetooth() {
        if (btAdapter != null && !btAdapter.isEnabled) {
            var isGranted = false
            if (Build.VERSION.SDK_INT >= BluetoothPermissionConstants.BLUETOOTH_CONNECT_MIN_SDK) {
                isGranted = ContextCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED
            }
            if (!isGranted) return

            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            val enableBtLauncher = activity.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    Toast.makeText(activity, "Bluetooth Turned ON", Toast.LENGTH_SHORT).show()
                    startDiscovery()
                }
            }
            enableBtLauncher.launch(enableBtIntent)
        }
    }

    fun startDiscovery() {
        val isBluetoothScanDenied = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
                ContextCompat.checkSelfPermission(activity,Manifest.permission.BLUETOOTH_SCAN) ==
                PackageManager.PERMISSION_DENIED
        if (isBluetoothScanDenied || btAdapter?.isEnabled == false) {
            Toast.makeText(activity, "Couldn't scan devices. Check permissions or your Bluetooth", Toast.LENGTH_SHORT).show()
        } else {
            btAdapter?.startDiscovery()
            Toast.makeText(activity, "Scanning successful", Toast.LENGTH_SHORT).show()
        }
    }

    // Registers the provided BroadcastReceiver to listen for Bluetooth device discovery events.
    fun registerDeviceDiscoveryReceiver(receiver: BroadcastReceiver) {
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        activity.registerReceiver(receiver, filter)
    }

    /*
      Custom BroadcastReceiver for handling discovered Bluetooth devices, checking permissions,
      creating Device objects from discovered devices, and updating the device list in the
      devicesViewModel if a new device is found
    */
    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    if (device != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.BLUETOOTH_CONNECT
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            return
                        }

                        val name = device.name ?: "Unknown device"
                        val btType = Device.getBluetoothType(device.type)
                        val bondState = Device.getBondState(device.bondState)
                        val deviceFound = Device(name, device.address, btType, bondState, deviceLatitude, deviceLongitude)
                        setLocationByDeviceType(deviceFound)
                        val existingDevice = deviceList.value.find { it.macAddress == deviceFound.macAddress }
                        if (existingDevice == null) {
                            val updatedList = deviceList.value + deviceFound
                            devicesViewModel.updateDeviceList(updatedList)
                            if(!User.instance.favourites.containsKey(deviceFound.macAddress)) {
                                User.instance.favourites[deviceFound.macAddress] = deviceFound
                                User.instance.updateDatabase()
                            }

                        }
                    }
                }
            }
        }
    }

    fun setCoordinates(latitude: Double, longitude: Double) {
        deviceLatitude = latitude
        deviceLongitude = longitude
    }

    fun setLocationByDeviceType(device: Device) {
        when(device.btType) {
            "Bluetooth Class 1" ->  {
                val dist = pickRandomDistance(0.1)
                device.longitude = calculateNewLongitude(device.longitude, dist)
                device.latitude = calculateNewLatitude( device.latitude, dist)
            }
            "Bluetooth Class 2" -> {
                val dist = pickRandomDistance(0.01)
                device.longitude = calculateNewLongitude(device.longitude, dist)
                device.latitude = calculateNewLatitude( device.latitude, dist)
            }// set 10m

            "Bluetooth Class 3" -> {
                val dist = pickRandomDistance(0.001)
                device.longitude = calculateNewLongitude(device.longitude, dist)
                device.latitude = calculateNewLatitude( device.latitude, dist)
            }
        }
    }
    private fun calculateNewLatitude(latitude: Double, distance: Double): Double {
        return   latitude  + (distance / r_earth ) * (180 / Math.PI);
    }
    private fun calculateNewLongitude(longitude: Double, distance: Double): Double {
        return   longitude + (distance / r_earth) * (180 / Math.PI) / cos(longitude * Math.PI/180);
    }

    private fun pickRandomDistance(range: Double): Double {
        return Math.random() * range;
    }
}