package com.example.bikezone.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.bikezone.navigation.Routes

@Composable
fun BikeZoneNavHost(
    navController: NavHostController , auth: Boolean
) {
    val startDestination = if (!auth) Routes.AuthRoute.route else Routes.AppRoute.route
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        setupAuthGraph(navController)
        setupAppGraph(navController)

    }
}