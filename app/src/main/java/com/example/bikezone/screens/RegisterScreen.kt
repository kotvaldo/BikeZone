package com.example.bikezone.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.bikezone.navigation.AuthScreens
import com.example.bikezone.navigation.Routes
import com.example.bikezone.ui.theme.BikeZoneTheme

@Composable
fun RegisterScreen(navController: NavController) {
    BikeZoneTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Welcome to Register",
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Button(
                    onClick = {
                        navController.navigate(Routes.AppRoute.route) {
                            popUpTo(Routes.AuthRoute.route) {
                                inclusive = false
                            }
                            launchSingleTop = true
                            restoreState = true

                        }
                    }
                ) {
                    Text(text = "Register")
                }

                Button(
                    onClick = {
                        navController.navigate(AuthScreens.Login.route) {
                        }
                    }
                ) {
                    Text(text = "Login")
                }
            }


        }
    }
}