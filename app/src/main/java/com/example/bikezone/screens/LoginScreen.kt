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
import androidx.compose.ui.res.stringResource
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
                    contentDescription = stringResource(id = R.string.str_logo_image) ,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f) // Nastavenie výšky obrázku
                        .background(DarkPrimary),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = stringResource(id = R.string.str_login),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                SimpleTextField(label = stringResource(id = R.string.str_email), modifier = Modifier.padding(bottom = 10.dp, top = 20.dp))
                SimpleTextField(label = stringResource(id = R.string.str_password), modifier = Modifier.padding(bottom = 10.dp))

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
                    Text(text = stringResource(id = R.string.str_login))
                }

                Text(
                    text = stringResource(id = R.string.str_not_have_account),
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
                    Text(text = stringResource(id = R.string.str_register))
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