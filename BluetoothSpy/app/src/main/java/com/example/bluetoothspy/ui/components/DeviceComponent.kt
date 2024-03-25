package com.example.bluetoothspy.ui.components

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bluetoothspy.constants.CommonConstants.MEDIUM_FONT_SIZE
import com.example.bluetoothspy.constants.CommonConstants.SMALL_FONT_SIZE
import com.example.bluetoothspy.models.Device
import com.example.bluetoothspy.models.User
import androidx.core.content.ContextCompat.startActivity

@Composable
fun DeviceComponent(device: Device) {
    var expanded by remember { mutableStateOf(false) }

    Card(colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primary),
        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)) {
        Column(modifier = Modifier.padding(end = 5.dp), horizontalAlignment = Alignment.CenterHorizontally ) {
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .animateContentSize()
            ){
                CardContent(device = device, expanded)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {expanded = !expanded}) {
                    Icon(imageVector = if(expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown , contentDescription = if(expanded) "show less details" else  "show more details" )
                }
            }
            if(expanded) {
                ButtonsBox(device)
            }
        }
    }
}

@Composable
fun CardContent(device: Device, expanded: Boolean) {
   Column(modifier = Modifier.padding(10.dp)) {
        TextComponent(textValue = device.name, textSize = MEDIUM_FONT_SIZE, colorValue = MaterialTheme.colorScheme.onPrimary, fontWeightValue = FontWeight.Bold )
        Spacer(modifier = Modifier.padding(10.dp))
        TextComponent(textValue = device.macAddress, textSize = SMALL_FONT_SIZE, colorValue = MaterialTheme.colorScheme.onPrimary, fontWeightValue = FontWeight.W600  )
        Spacer(modifier = Modifier.padding(10.dp))
       if(expanded) {
           TextComponent(textValue = device.btType.toString(), textSize = SMALL_FONT_SIZE, colorValue = MaterialTheme.colorScheme.onPrimary, fontWeightValue = FontWeight.W600, )
           Spacer(modifier = Modifier.padding(10.dp))
           TextComponent(textValue = device.bondState.toString(), textSize = SMALL_FONT_SIZE, colorValue = MaterialTheme.colorScheme.onPrimary, fontWeightValue = FontWeight.W600 )
           Spacer(modifier = Modifier.padding(10.dp))
       }
   }
}

@Composable()
fun ButtonsBox(device: Device) {
    val context = LocalContext.current

    Row(modifier = Modifier.padding(10.dp)) {
        IconButton(onClick = { shareDetails(context, device) }) {
            Icon(imageVector = Icons.Filled.Send , contentDescription = "send_icon" )
        }
        Spacer(modifier = Modifier.padding(10.dp))
        IconButton(onClick = { saveDevice(context, device) }) {
            Icon(imageVector = Icons.Filled.Favorite , contentDescription = "fav_icon" )
        }
    }
}

fun saveDevice(context: Context, device: Device ) {
    device.isFavourite = true
    val userData = User.instance
    userData.favourites.replace(device.macAddress, device)
    userData.updateDatabase()
    Toast.makeText(context, "Device saved.", Toast.LENGTH_SHORT).show()
}

fun shareDetails(context: Context, device: Device) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, device.toJsonString())
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(intent, "Share device details")
    context.startActivity(shareIntent)
}