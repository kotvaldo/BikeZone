package com.example.bikezone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bikezone.navigation.Routes
import com.example.bikezone.navigation.Screens
import com.example.bikezone.navigation.SetupNavGraph
import com.example.bikezone.ui.theme.BikeZoneTheme

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private lateinit var screens: MutableList<Screens>
    private lateinit var currentScreen: MutableState<String?>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BikeZoneTheme {
                screens = mutableListOf(Screens.Home, Screens.Cart, Screens.Profile, Screens.About)
                navController = rememberNavController()
                SetupNavGraph(navController = navController)

                currentScreen = remember {
                    mutableStateOf(navController.currentBackStackEntry?.destination?.route)
                }
                LaunchedEffect(navController) {
                    val callback = NavController.OnDestinationChangedListener { _, _, _ ->
                        currentScreen.value = navController.currentBackStackEntry?.destination?.route
                    }
                    navController.addOnDestinationChangedListener(callback)
                }
                if (currentScreen.value == Routes.AppRoute.route || screens.any { it.route == currentScreen.value })
                BikeZoneApp(navController = navController, currentScreen)
            }
        }
    }
}