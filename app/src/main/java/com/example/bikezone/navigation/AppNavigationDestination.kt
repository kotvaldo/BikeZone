package com.example.bikezone.navigation

import androidx.compose.ui.graphics.vector.ImageVector

interface AppNavigationDestination {
    val route: String
    val titleRes: Int
    val selectedIcon: ImageVector
    val unselectedIcon : ImageVector
}