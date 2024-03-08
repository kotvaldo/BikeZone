package com.example.bikezone.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
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
            ConstraintLayout {
                val image = createRef()
                val text = createRef()
                val row = createRef()
                val textFieldName = createRef()
                val textFieldEmail = createRef()
                val textFieldPassword = createRef()

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logoImage", // popis obsahu môžete nastaviť podľa potreby
                    modifier = Modifier
                        .fillMaxHeight(0.3f)
                        .fillMaxWidth()
                        .constrainAs(image) {
                            top.linkTo(parent.top) // Viazanie obrázka k hornému okraju kontajnera
                            start.linkTo(parent.start) // Viazanie obrázka k ľavému okraju kontajnera
                            end.linkTo(parent.end) // Viazanie obrázka k pravému okraju kontajnera
                        }
                        .background(DarkPrimary),

                    contentScale = ContentScale.Fit,
                )
                Text(
                    text = "Login",
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.constrainAs(text) {
                        top.linkTo(image.bottom, margin = 30.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    textAlign = TextAlign.Center,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold

                )
                SimpleTextField(label = "Name",
                    modifier = Modifier.constrainAs(textFieldName) {
                        top.linkTo(text.bottom, margin = 10.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
                SimpleTextField(label = "E-Mail",
                    modifier = Modifier.constrainAs(textFieldEmail) {
                        top.linkTo(textFieldName.bottom, margin = 10.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
                SimpleTextField(label = "Password",
                    modifier = Modifier.constrainAs(textFieldPassword) {
                        top.linkTo(textFieldEmail.bottom, margin = 10.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
                Row(
                    modifier = Modifier.constrainAs(row) {
                        top.linkTo(textFieldPassword.bottom, margin = 10.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                ) {
                    Button(
                        onClick = {
                            navController.navigate(Routes.AppRoute.route) {
                                popUpTo(Routes.AuthRoute.route) {
                                    inclusive = false
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    ) {
                        Text(text = "Login")
                    }

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

}

@Composable
fun SimpleTextField(label: String, modifier: Modifier) {
    var text by remember { mutableStateOf(TextFieldValue()) }

    TextField(modifier = modifier,
        value = text,
        onValueChange = { text = it },
        label = { Text(text = label) }
    )
}