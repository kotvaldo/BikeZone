package com.example.bikezone.navigation


sealed class AuthScreens(
    val route: String,
    val title: String


) {
    data object Login : AuthScreens(
        route = "nav_login",
        title = "Login"
    )

    data object Register : AuthScreens(
        route = "nav_register",
        title = "Register"
    )
}