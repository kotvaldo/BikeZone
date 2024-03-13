package com.example.bikezone.navigation.graph

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.bikezone.navigation.AuthScreens
import com.example.bikezone.navigation.Routes
import com.example.bikezone.screens.LoginScreen
import com.example.bikezone.screens.RegisterScreen

@OptIn(ExperimentalMaterial3Api::class)
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