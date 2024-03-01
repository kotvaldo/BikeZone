package com.example.bikezone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bikezone.screens.CartScreen
import com.example.bikezone.screens.HomeScreen
import com.example.bikezone.screens.ProfileScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    ) {
        composable(
            route = Screens.Home.route
        ) {
            HomeScreen()
        }
        composable(
            route = Screens.Profile.route
        ) {
            ProfileScreen()
        }
        composable(
            route = Screens.Cart.route
        ) {
            CartScreen()
        }
    }
}