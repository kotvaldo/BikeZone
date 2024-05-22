package com.example.bikezone

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bikezone.navigation.CartDestination
import com.example.bikezone.navigation.HomeDestination
import com.example.bikezone.navigation.OrderDestination
import com.example.bikezone.navigation.ProfileDestination
import com.example.bikezone.navigation.graph.BikeZoneNavHost


@Composable
fun BikeZoneApp(navController: NavHostController = rememberNavController()) {
    BikeZoneNavHost(navController)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BikeZoneTopAppBar(
    @StringRes title: Int,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = title),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateBack) {
                    Icon(
                        Icons.Rounded.ArrowBackIosNew,
                        contentDescription = stringResource(id = R.string.cd_nav_back)
                    )
                }
            }
        },
        modifier = modifier
        // Ak chcete použiť scrollBehavior, pridajte ho sem
        // scrollBehavior = scrollBehavior
    )
}
@Composable
fun BikeZoneBottomAppBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val bottomNavBarScreens = listOf(HomeDestination, CartDestination, ProfileDestination, OrderDestination)
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination

    NavigationBar(
        modifier = modifier.background(color = Color.Black),
    ) {
        bottomNavBarScreens.forEach { screen ->
            val isSelected = currentDestination?.route == screen.route
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (isSelected) screen.selectedIcon else screen.unselectedIcon,
                        contentDescription = stringResource(id = screen.titleRes) + " icon",
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = screen.titleRes),
                        fontFamily = FontFamily.Default,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(HomeDestination.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}