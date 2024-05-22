package com.example.bikezone.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.bikezone.navigation.AboutDestination
import com.example.bikezone.navigation.CartDestination
import com.example.bikezone.navigation.HomeDestination
import com.example.bikezone.navigation.OrderDestination
import com.example.bikezone.navigation.ProfileDestination
import com.example.bikezone.navigation.Routes
import com.example.bikezone.ui.about.AboutScreen
import com.example.bikezone.ui.cart.CartScreen
import com.example.bikezone.ui.home.HomeScreen
import com.example.bikezone.ui.order.OrderScreen
import com.example.bikezone.ui.profile.ProfileScreen

fun NavGraphBuilder.setupAppGraph(navController: NavHostController) {
    navigation(startDestination = HomeDestination.route, route = Routes.AppRoute.route) {
        composable(
            route = HomeDestination.route
        ) {
            HomeScreen(navController)
        }
        composable(
            route = ProfileDestination.route
        ) {
            ProfileScreen(navController)
        }
        composable(
            route = CartDestination.route
        ) {
            CartScreen(navController)
        }
        composable(
            route = AboutDestination.route
        ) {
            AboutScreen(navController)
        }

        composable(
            route = OrderDestination.route
        ) {
            OrderScreen(navController)
        }


    }


}