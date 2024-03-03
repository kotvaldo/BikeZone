package com.example.bikezone.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.bikezone.screens.AboutScreen
import com.example.bikezone.screens.CartScreen
import com.example.bikezone.screens.HomeScreen
import com.example.bikezone.screens.ProfileScreen

fun NavGraphBuilder.setupAppGraph(navController: NavController) {
    navigation(startDestination = Screens.Home.route, route = Routes.AppRoute.route) {
        composable(
            route = Screens.Home.route
        ) {
            HomeScreen(navController)
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
        composable(
            route = Screens.About.route
        ) {
            AboutScreen()
        }


    }


}