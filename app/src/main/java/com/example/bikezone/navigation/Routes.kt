package com.example.bikezone.navigation

sealed class Routes(
    val route: String
) {
    data object AuthRoute : Routes(
        route = "Auth"
    )

    data object AppRoute : Routes(
        route = "App"
    )
}