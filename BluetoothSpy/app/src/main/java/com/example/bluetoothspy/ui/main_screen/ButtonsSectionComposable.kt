package com.example.bluetoothspy.ui.main_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bluetoothspy.R
import com.example.bluetoothspy.constants.CommonConstants
import com.example.bluetoothspy.constants.SplashScreenConstants
import com.example.bluetoothspy.models.Device
import com.example.bluetoothspy.models.User
import com.example.bluetoothspy.ui.components.TextComponent
import com.example.bluetoothspy.ui.user_dialog.ShowDeviceFavourites
import com.example.bluetoothspy.utils.ThemeStore
import kotlinx.coroutines.launch

@Composable
fun ButtonsSection() {
    Surface(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)){
            Box(modifier = Modifier.weight(1f)) {
                SwapTheme()
            }
            Box(modifier = Modifier.weight(1f)) {
                Favorites()
            }
        }
    }

}

@Composable
fun SwapTheme() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val  dataStore = ThemeStore(context)
    val savedTheme = dataStore.getTheme.collectAsState(initial = false)
    val isDarkTheme = savedTheme.value ?: isSystemInDarkTheme()
    Button(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()
        .height(50.dp),
        onClick = {
        scope.launch {
            dataStore.saveTheme(!isDarkTheme)
            val toastText = if(isDarkTheme) "Light Theme enabled" else "Dark Theme enabled"
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
        }
    } ) {
            Image(
                modifier = Modifier
                    .padding(CommonConstants.SMALL_PADDING)
                    .size(SplashScreenConstants.EXTRA_SMALL_LOGO_SIZE),
                painter = if(isDarkTheme) painterResource(id = R.drawable.moon) else painterResource(id = R.drawable.sun),
                contentDescription = SplashScreenConstants.LOGO_IMAGE_DESCRIPTION
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            TextComponent(textValue = "Swap Theme", textSize = 14.sp, colorValue = MaterialTheme.colorScheme.onPrimary, fontWeightValue = FontWeight.Bold)
    }
}

@Composable
fun Favorites() {
    var showDialog by remember { mutableStateOf(false) }

    Button(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(50.dp),
        onClick = { showDialog = true }
    ) {
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        TextComponent(
            textValue = "Saved",
            textSize = 14.sp,
            colorValue = MaterialTheme.colorScheme.onPrimary,
            fontWeightValue = FontWeight.Bold
        )
    }

    if (showDialog) {
        ShowFavourites { showDialog = false }
    }
}

@Composable
fun ShowFavourites(onDialogDismissed: () -> Unit) {
    val context = LocalContext.current
    val userData = User.instance
    var favourites by remember { mutableStateOf<List<Device>>(emptyList()) }
    var isDataLoaded by remember { mutableStateOf(false) }


    userData.retrieveFromDatabase { fetchedData ->
        favourites = fetchedData.filter { it.isFavourite }
        isDataLoaded = true
    }

    if (isDataLoaded) {
        if (favourites.isEmpty()){
            Toast.makeText(context, "No saved Devices", Toast.LENGTH_SHORT).show()
            onDialogDismissed()
        } else {
            ShowDeviceFavourites(devices = favourites, onDialogDismissed)
        }
    }
}


@Preview
@Composable
fun ButtonPreview() {
    ButtonsSection()
}