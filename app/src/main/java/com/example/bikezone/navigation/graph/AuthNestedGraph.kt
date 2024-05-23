package com.example.bikezone.navigation.graph

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.bikezone.navigation.LoginDestination
import com.example.bikezone.navigation.RegisterDestination
import com.example.bikezone.navigation.Routes
import com.example.bikezone.ui.auth.LoginScreen
import com.example.bikezone.ui.auth.RegisterScreen

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.setupAuthGraph(navController: NavHostController) {

    navigation(startDestination = LoginDestination .route, route = Routes.AuthRoute.route) {
        composable(
            route = LoginDestination.route
        ) {
            LoginScreen(navController)

        }

        composable(
            route = RegisterDestination.route
        ) {
            RegisterScreen(navController)
        }
    }

}