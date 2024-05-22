package com.example.bikezone.ui.auth

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import androidx.navigation.NavHostController
import com.example.bikezone.R
import com.example.bikezone.navigation.LoginDestination
import com.example.bikezone.ui.components.CustomTextField
import com.example.bikezone.ui.theme.BikeZoneTheme
import com.example.bikezone.ui.theme.CustomRed
import com.example.bikezone.ui.theme.DarkPrimary

@ExperimentalMaterial3Api
@Composable
fun RegisterScreen(navController: NavHostController) {
    val (name, setName) = rememberSaveable {
        mutableStateOf("")
    }
    val (email, setEmail) = rememberSaveable {
        mutableStateOf("")
    }
    val (password, setPassword) = rememberSaveable {
        mutableStateOf("")
    }
    val (repeatPasswd, setRepeatPasswd) = rememberSaveable {
        mutableStateOf("")
    }
    val (address, setAddress) = rememberSaveable {
        mutableStateOf("")
    }

    val (checked, onCheckedChange) = rememberSaveable {
        mutableStateOf(false)
    }

    LocalContext.current

    var isPasswordSame by remember {
        mutableStateOf(false)
    }
    val isFieldsNotEmpty = name.isNotEmpty() && email.isNotEmpty() &&
            password.isNotEmpty() && repeatPasswd.isNotEmpty() &&
            address.isNotEmpty() && checked

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
                        .fillMaxHeight(0.1f) // Nastavenie výšky obrázku
                        .background(DarkPrimary),
                    contentScale = ContentScale.Fit
                )
                AnimatedVisibility(isPasswordSame) {
                    Text(
                        text = stringResource(id = R.string.str_password_not_match),
                        color = MaterialTheme.colorScheme.error,
                    )
                }
                Text(
                    text = stringResource(id = R.string.str_register),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                CustomTextField(
                    label = stringResource(id = R.string.str_name),
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth(0.8f),
                    value = name,
                    onValueChange = setName,
                    icon = Icons.Default.Person

                )
                CustomTextField(
                    label = stringResource(id = R.string.str_email),
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth(0.8f),
                    value = email,
                    onValueChange = setEmail,
                    icon = Icons.Default.Email,
                    keyboardType = KeyboardType.Email,
                )
                CustomTextField(
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
                CustomTextField(
                    label = stringResource(id = R.string.str_repeat_password),
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth(0.8f),
                    value = repeatPasswd,
                    onValueChange = setRepeatPasswd,
                    keyboardType = KeyboardType.Password,
                    visualTransformation = PasswordVisualTransformation(),
                    icon = Icons.Default.Lock
                )
                CustomTextField(
                    label = stringResource(id = R.string.str_address),
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth(0.8f),
                    value = address,
                    onValueChange = setAddress,
                    icon = Icons.Default.Home
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = onCheckedChange
                    )
                    Text(
                        text = stringResource(id = R.string.str_terms_agree),
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
                Button(
                    enabled = isFieldsNotEmpty,
                    onClick = {
                        isPasswordSame = password != repeatPasswd
                        if(!isPasswordSame) {
                            navController.navigate(LoginDestination.route) {
                                popUpTo(LoginDestination.route) {
                                    inclusive = true
                                }
                                launchSingleTop = true

                            }
                        }

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary)
                ) {
                    Text(text = stringResource(id = R.string.str_register))
                }

                Spacer(modifier = Modifier.height(50.dp))
                Row {
                    val signUpOther = stringResource(id = R.string.str_already_registered)
                    val signUp = stringResource(id = R.string.str_login)
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
                                    navController.navigate(LoginDestination.route) {
                                        popUpTo(LoginDestination.route) {
                                            inclusive = true
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