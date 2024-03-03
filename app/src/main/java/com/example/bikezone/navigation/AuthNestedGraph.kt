package com.example.bikezone.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.bikezone.screens.LoginScreen
import com.example.bikezone.screens.RegisterScreen

fun NavGraphBuilder.setupAuthGraph(navController: NavController) {

    navigation(startDestination = AuthScreens.Login.route, route = Routes.AuthRoute.route) {
        composable(
            route = AuthScreens.Login.route
        ) {
            LoginScreen(navController)

        }

        composable(
            route = AuthScreens.Register.route
        ) {
            RegisterScreen(navController)
        }
    }

}