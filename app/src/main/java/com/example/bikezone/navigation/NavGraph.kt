package com.example.bikezone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Routes.AuthRoute.route
    ) {

        setupAppGraph(navController)
        setupAuthGraph(navController)

    }
}