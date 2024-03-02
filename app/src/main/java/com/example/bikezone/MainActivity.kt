package com.example.bikezone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.example.bikezone.ui.theme.BikeZoneTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.lang.reflect.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BikeZoneTheme {

                BikeZoneApp()
            }
        }
    }
}