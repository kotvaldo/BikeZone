package com.example.bikezone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bikezone.navigation.SetupNavGraph
import com.example.bikezone.ui.theme.BikeZoneTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController: NavHostController = rememberNavController()
            BikeZoneTheme {
                SetupNavGraph(navController = navController)
            //BikeZoneApp()
            }
        }
    }
}