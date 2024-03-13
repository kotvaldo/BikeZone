package com.example.bikezone.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bikezone.R
import com.example.bikezone.components.AuthTextField
import com.example.bikezone.navigation.AuthScreens
import com.example.bikezone.navigation.Routes
import com.example.bikezone.ui.theme.BikeZoneTheme
import com.example.bikezone.ui.theme.CustomRed
import com.example.bikezone.ui.theme.DarkPrimary

@ExperimentalMaterial3Api
@Composable
fun LoginScreen(navController: NavController) {
    val (email, setEmail) = rememberSaveable {
        mutableStateOf("")
    }
    val (password, setPassword) = rememberSaveable {
        mutableStateOf("")
    }


    val isFieldsEmpty = email.isNotEmpty() && password.isNotEmpty()
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
                    contentDescription = stringResource(id = R.string.str_logo_image),
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

                AuthTextField(
                    label = stringResource(id = R.string.str_email),
                    modifier = Modifier
                        .padding(bottom = 10.dp, top = 20.dp)
                        .fillMaxWidth(0.8f),
                    value = email,
                    onValueChange = setEmail,
                    icon = Icons.Default.Email,
                    keyboardType = KeyboardType.Email


                )
                AuthTextField(
                    label = stringResource(id = R.string.str_password),
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth(0.8f),
                    value = password,
                    onValueChange = setPassword,
                    icon = Icons.Default.Lock,
                    keyboardType = KeyboardType.Password,
                    visualTransformation = PasswordVisualTransformation()
                )

                Button(
                    enabled = isFieldsEmpty,
                    onClick = {
                        navController.navigate(Routes.AppRoute.route) {
                            popUpTo(Routes.AuthRoute.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    )
                ) {
                    Text(text = stringResource(id = R.string.str_login))
                }
                Spacer(modifier = Modifier.height(50.dp))
                Row {
                    val signUpOther = stringResource(id = R.string.str_not_have_account)
                    val signUp = stringResource(id = R.string.str_register_down)
                    val annotatedString = buildAnnotatedString {
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.onPrimary)) {
                            append(signUpOther)
                        }
                        append("  ")
                        withStyle(
                            SpanStyle(color = CustomRed, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            ) {
                            pushStringAnnotation(tag = signUp, signUp)
                            append(signUp)
                        }
                    }

                    ClickableText(
                        annotatedString,
                    ) { offset ->
                        annotatedString.getStringAnnotations(offset, offset).forEach {
                            when (it.tag) {
                                signUp -> {
                                    navController.navigate(AuthScreens.Register.route) {
                                        popUpTo(AuthScreens.Login.route) {
                                            inclusive = false
                                        }
                                        launchSingleTop = true
                                    }
                                }


                            }
                        }
                    }
                }


            }

        }
    }
}
