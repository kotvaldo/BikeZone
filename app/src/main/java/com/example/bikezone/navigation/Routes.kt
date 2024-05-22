package com.example.bikezone.navigation

sealed class Routes(
    val route: String
) {
    data object AuthRoute : Routes(
        route = "auth"
    )

    data object AppRoute : Routes(
        route = "app"
    )
}