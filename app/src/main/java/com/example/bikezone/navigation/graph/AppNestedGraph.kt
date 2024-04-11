package com.example.bikezone.navigation.graph

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.bikezone.navigation.Routes
import com.example.bikezone.navigation.Screens
import com.example.bikezone.ui.screens.AboutScreen
import com.example.bikezone.ui.screens.CartScreen
import com.example.bikezone.ui.screens.HomeScreen
import com.example.bikezone.ui.screens.ProfileScreen

fun NavGraphBuilder.setupAppGraph(navController: NavController, sharedViewModel: ViewModel) {
    navigation(startDestination = Screens.Home.route, route = Routes.AppRoute.route) {
        composable(
            route = Screens.Home.route
        ) {
            HomeScreen(navController, sharedViewModel)
        }
        composable(
            route = Screens.Profile.route
        ) {
            ProfileScreen(navController, sharedViewModel)
        }
        composable(
            route = Screens.Cart.route
        ) {
            CartScreen(navController, sharedViewModel)
        }
        composable(
            route = Screens.About.route
        ) {
            AboutScreen(navController, sharedViewModel)
        }


    }


}