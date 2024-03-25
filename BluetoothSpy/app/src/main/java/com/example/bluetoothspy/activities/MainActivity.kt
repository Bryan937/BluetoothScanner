package com.example.bluetoothspy.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bluetoothspy.models.Device
import com.example.bluetoothspy.models.User
import com.example.bluetoothspy.ui.main_screen.MainScreen
import com.example.bluetoothspy.services.BluetoothService
import com.example.bluetoothspy.services.LocationService
import com.example.bluetoothspy.ui.theme.BluetoothSpyTheme
import com.example.bluetoothspy.utils.Permissions
import com.example.bluetoothspy.viewModels.DeviceViewModel


class MainActivity : ComponentActivity() {

    private val deviceViewModel : DeviceViewModel by viewModels()
    private lateinit var bluetoothService : BluetoothService



    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        super.onCreate(savedInstanceState)

        bluetoothService = BluetoothService(this, deviceViewModel)
        val locationService = LocationService(this, bluetoothService, deviceViewModel)
        val permissionManager = Permissions(bluetoothService, locationService, this)

        permissionManager.checkInitialPermissions()
        bluetoothService.enableBluetooth()
        bluetoothService.registerDeviceDiscoveryReceiver(bluetoothService.receiver)
        locationService.getDeviceCoordinates()



        setContent {
            BluetoothSpyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    MainScreen(deviceViewModel)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(bluetoothService.receiver)
    }

}