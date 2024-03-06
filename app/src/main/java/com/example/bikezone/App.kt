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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bikezone.navigation.Screens
import com.example.bikezone.ui.theme.fontFamily
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BikeZoneApp(
    navController: NavHostController,
    currentScreen: MutableState<String?>,
) {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val drawerScreens = mutableListOf(Screens.Profile, Screens.About)
    val bottomNavBarScreens = mutableListOf(Screens.Home, Screens.Cart)


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
                                        popUpTo(Screens.Home.route) {
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
                                fontFamily = fontFamily,
                                color = MaterialTheme.colorScheme.onPrimary
                                ,text = currentScreen.value?.let { route ->
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
                    NavigationBar {
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
                                label = { Text(text = screen.title, fontFamily = fontFamily, color = MaterialTheme.colorScheme.onPrimary) },
                                selected = screen.route == currentScreen.value,
                                onClick = {
                                    // Ak nie sme na HomeScreen, prepneme na neho
                                    navController.navigate(screen.route) {
                                        popUpTo(Screens.Home.route) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                    currentScreen.value = screen.route
                                }
                            )
                        }
                    }
                }

            ) {
            }
        }

    }

}
