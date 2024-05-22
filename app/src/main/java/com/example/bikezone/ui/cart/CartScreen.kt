package com.example.bikezone.ui.cart

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.bikezone.BikeZoneBottomAppBar
import com.example.bikezone.BikeZoneTopAppBar
import com.example.bikezone.R
import com.example.bikezone.navigation.HomeDestination
import com.example.bikezone.ui.home.HomeLayout
import com.example.bikezone.ui.theme.BikeZoneTheme

@Composable
fun CartScreen(navController: NavHostController) {
    BikeZoneTheme {
        Scaffold(
            topBar = {
                BikeZoneTopAppBar(
                    title = R.string.str_cart,
                    canNavigateBack = true,
                    navigateBack = {navController.navigate(HomeDestination.route)},
                    modifier = Modifier
                )
            },
            bottomBar = {
                BikeZoneBottomAppBar(
                    navController = navController
                )
            },
            content = { innerPadding ->
                CartLayout(
                    contentPadding = innerPadding
                )
            })
    }
}

@Composable
fun CartLayout(
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {

}