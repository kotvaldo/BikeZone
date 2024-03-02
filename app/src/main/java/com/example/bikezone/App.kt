@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.bikezone

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bikezone.navigation.Screens
import com.example.bikezone.navigation.SetupNavGraph
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BikeZoneApp(
) {
    val navController: NavHostController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val drawerScreens = mutableListOf(Screens.Profile)
    val bottomNavBarScreens = mutableListOf(Screens.Home, Screens.Cart)
    val currentScreen = remember {
        mutableStateOf(navController.currentBackStackEntry?.destination?.route)
    }


    LaunchedEffect(navController) {
        val callback = NavController.OnDestinationChangedListener { _, _, _ ->
            currentScreen.value = navController.currentBackStackEntry?.destination?.route
        }
        navController.addOnDestinationChangedListener(callback)
    }


    Box {
        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = true,
            drawerContent = {
                ModalDrawerSheet {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.secondary)
                            .height(150.dp)
                    ) {
                    }
                    Spacer(
                        modifier = Modifier
                            .height(20.dp)
                            .fillMaxWidth()
                    ) // Add more screens as needed
                    Column(
                        Modifier
                            .fillMaxSize()
                            .background(
                                MaterialTheme.colorScheme.primary
                            )
                    ) {
                        drawerScreens.forEach { screen ->
                            NavigationDrawerItem(
                                label = { Text(text = screen.title) },
                                selected = screen.route == currentScreen.value,
                                icon = {
                                    Icon(
                                        imageVector = if (screen.route == currentScreen.value) {
                                            screen.selectedIcon
                                        } else screen.unselectedIcon,
                                        contentDescription = "${screen.title} icon"
                                    )
                                },
                                onClick = {
                                    coroutineScope.launch {
                                        drawerState.close()
                                    }
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                    currentScreen.value = screen.route

                                },
                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                            )
                        }

                    }

                }
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = currentScreen.value?.let { route ->
                                    drawerScreens.firstOrNull { it.route == route }?.title
                                        ?: bottomNavBarScreens.firstOrNull { it.route == route }?.title
                                        ?: "BikeZone"
                                } ?: "BikeZone")
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    drawerState.open()
                                }
                            }) {
                                Icon(
                                    Icons.Rounded.Menu, contentDescription = "MenuButton"
                                )
                            }
                        }
                    )
                }, bottomBar = {
                    NavigationBar(

                    ) {
                        bottomNavBarScreens.forEach { screen ->
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        imageVector = if (screen.route == currentScreen.value) {
                                            screen.selectedIcon
                                        } else screen.unselectedIcon,
                                        contentDescription = "${screen.title} icon"
                                    )
                                },
                                label = { Text(text = screen.title) },
                                selected = screen.route == currentScreen.value,
                                onClick = {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        // Restore state when reselecting a previously selected item
                                        restoreState = true
                                    }
                                    currentScreen.value = screen.route
                                }
                            )
                        }
                    }
                }

            ) {
                SetupNavGraph(navController = navController)
            }
        }

    }

}