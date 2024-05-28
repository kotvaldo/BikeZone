package com.example.bikezone.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Shop
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.bikezone.R


object HomeDestination : AppNavigationDestination{
    override val route: String = "nav_home"
    override val titleRes: Int = R.string.app_name
    override val selectedIcon: ImageVector = Icons.Filled.Home
    override val unselectedIcon: ImageVector = Icons.Outlined.Home

}

object CartDestination : AppNavigationDestination {
    override val route: String = "cart"
    override val titleRes: Int = R.string.str_cart
    override val selectedIcon = Icons.Filled.ShoppingCart
    override val unselectedIcon = Icons.Outlined.ShoppingCart
}
object ItemDetailsDestination : AppNavigationDestination {
    override val route = "item_details"
    override val titleRes = R.string.str_item_detail
    override val selectedIcon: ImageVector = Icons.Filled.Home
    override val unselectedIcon: ImageVector = Icons.Outlined.Home
    const val ITEM_ID_ARG = "itemId"
    val routeWithArgs = "$route/{$ITEM_ID_ARG}"
}

object ProfileDestination : AppNavigationDestination {
    override val route: String = "profile"
    override val titleRes: Int = R.string.str_profile
    override val selectedIcon = Icons.Filled.Person
    override val unselectedIcon = Icons.Outlined.Person
}

object AboutDestination : AppNavigationDestination {
    override val route: String = "about"
    override val titleRes: Int = R.string.str_about
    override val selectedIcon = Icons.Filled.Info
    override val unselectedIcon = Icons.Outlined.Info
}
object OrderDestination : AppNavigationDestination {
    override val route: String = "order"
    override val titleRes: Int = R.string.str_order
    override val selectedIcon = Icons.Filled.Shop
    override val unselectedIcon = Icons.Outlined.Shop
}


object LoginDestination : AuthNavigationDestinantion {
    override val route: String = "login"
    override val titleRes: Int = R.string.str_login
}

object RegisterDestination : AuthNavigationDestinantion
{
    override val route: String = "register"
    override val titleRes: Int = R.string.str_register
}