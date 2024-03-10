package com.example.bikezone.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bikezone.R
import com.example.bikezone.navigation.AuthScreens
import com.example.bikezone.navigation.Routes
import com.example.bikezone.ui.theme.BikeZoneTheme
import com.example.bikezone.ui.theme.DarkPrimary

@Composable
fun LoginScreen(navController: NavController) {
    BikeZoneTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()), // Pridanie posúvateľnosti
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logoImage",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f) // Nastavenie výšky obrázku
                        .background(DarkPrimary),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = "Login",
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                SimpleTextField(label = "E-Mail", modifier = Modifier.padding(bottom = 10.dp, top = 20.dp))
                SimpleTextField(label = "Heslo", modifier = Modifier.padding(bottom = 10.dp))

                Button(
                    onClick = {
                        navController.navigate(Routes.AppRoute.route) {
                            popUpTo(Routes.AuthRoute.route) {
                                inclusive = false
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                ) {
                    Text(text = "Login")
                }

                Text(
                    text = "Nemáte založený účet ?",
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(top = 16.dp),
                )

                Button(
                    onClick = {
                        navController.navigate(AuthScreens.Register.route)
                    },
                ) {
                    Text(text = "Register")
                }
            }

        }
    }
}

@Composable
fun SimpleTextField(label: String, modifier: Modifier) {
    var text by remember { mutableStateOf(TextFieldValue()) }

    TextField(modifier = modifier,
        value = text,
        onValueChange = { text = it },
        label = { Text(text = label) },
    )
}