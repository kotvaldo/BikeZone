package com.example.bikezone.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bikezone.BikeZoneBottomAppBar
import com.example.bikezone.BikeZoneTopAppBar
import com.example.bikezone.R
import com.example.bikezone.ui.theme.BikeZoneTheme

@Composable
fun HomeScreen(
    navController: NavHostController

) {
    BikeZoneTheme {
        Scaffold(
            topBar = {
                BikeZoneTopAppBar(
                    title = R.string.app_name,
                    canNavigateBack = false,
                    navigateBack = {},
                    modifier = Modifier
                )
            },
            bottomBar = {
                BikeZoneBottomAppBar(
                    navController = navController
                )
            },
            content = { innerPadding ->
               HomeLayout(
                   contentPadding = innerPadding
               )
            })
    }
}


@Composable
fun HomeLayout(
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {

}