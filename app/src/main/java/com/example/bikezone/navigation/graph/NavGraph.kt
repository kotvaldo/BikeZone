package com.example.bikezone.navigation.graph

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.bikezone.navigation.Routes

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    sharedViewModel: ViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Routes.AuthRoute.route
    ) {
        setupAuthGraph(navController,sharedViewModel)
        setupAppGraph(navController, sharedViewModel)

    }
}