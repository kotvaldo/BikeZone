package com.example.bikezone.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector


) {
    data object Home : Screens(
        route = "nav_home",
        title = "BikeZone",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )

    data object Cart : Screens(
        "nav_cart",
        title = "Košík",
        selectedIcon = Icons.Filled.ShoppingCart,
        unselectedIcon = Icons.Outlined.ShoppingCart
    )

    data object Profile : Screens(
        route = "nav_profile",
        title = "Profil",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person
        )

    data object About : Screens(
        route = "nav_about",
        title = "O nás",
        selectedIcon = Icons.Filled.Info,
        unselectedIcon = Icons.Outlined.Info
    )

}

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