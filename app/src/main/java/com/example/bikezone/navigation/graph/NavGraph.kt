package com.example.bikezone.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.bikezone.navigation.Routes

@Composable
fun BikeZoneNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Routes.AuthRoute.route,
    ) {
        setupAuthGraph(navController)
        setupAppGraph(navController)

    }
}