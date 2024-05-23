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
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bikezone.R
import com.example.bikezone.navigation.LoginDestination
import com.example.bikezone.ui.AppViewModelProvider
import com.example.bikezone.ui.components.CustomTextField
import com.example.bikezone.ui.theme.BikeZoneTheme
import com.example.bikezone.ui.theme.CustomRed
import com.example.bikezone.ui.theme.DarkPrimary
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    BikeZoneTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            RegisterBody(
                onRegisterClick = {
                    viewModel.verifyAlreadyExistAccount()
                    if (!viewModel.registerUiState.alreadyExist) {
                        coroutineScope.launch {
                            viewModel.saveItem()
                            navController.navigate(LoginDestination.route)
                        }

                    }
                },
                navController = navController,
                onValueChange = viewModel::updateUiState,
                registerState = viewModel.registerUiState,
                onTermsAcceptedChange = viewModel::updateStateTermsAccept,
                isFieldsNotEmpty = viewModel.verifyIsFieldsNotEmpty() && viewModel.verifyTermsAccepted(),
                alreadyExist = viewModel.registerUiState.alreadyExist

            )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun RegisterBody(
    onRegisterClick: () -> Unit,
    navController: NavHostController,
    registerState: RegisterState,
    onTermsAcceptedChange: ((Boolean) -> Unit),
    onValueChange: (RegisterUserDetails) -> Unit,
    isFieldsNotEmpty: Boolean,
    alreadyExist: Boolean
) {
    RegisterInputForm(
        isPasswordSame = registerState.isPasswordSame,
        userDetails = registerState.userDetails,
        navController = navController,
        termsAccepted = registerState.termsAccepted,
        onTermsAcceptedChange = onTermsAcceptedChange,
        isFieldsNotEmpty = isFieldsNotEmpty,
        onValueChange = onValueChange,
        onRegisterClick = onRegisterClick,
        alreadyExist = alreadyExist
    )
}


@ExperimentalMaterial3Api
@Composable
fun RegisterInputForm(
    isPasswordSame: Boolean,
    alreadyExist: Boolean,
    userDetails: RegisterUserDetails,
    modifier: Modifier = Modifier,
    onValueChange: (RegisterUserDetails) -> Unit = {},
    navController: NavHostController,
    termsAccepted: Boolean,
    onTermsAcceptedChange: ((Boolean) -> Unit),
    isFieldsNotEmpty: Boolean,
    onRegisterClick: () -> Unit
) {

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()), // Pridanie posúvateľnosti
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(id = R.string.str_logo_image),
            modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f) // Nastavenie výšky obrázku
                .background(DarkPrimary),
            contentScale = ContentScale.Fit
        )
        AnimatedVisibility(visible = !isPasswordSame) {
            Text(
                text = stringResource(id = R.string.str_password_not_match),
                color = MaterialTheme.colorScheme.error,
            )
        }
        AnimatedVisibility(visible = alreadyExist) {
            Text(
                text = stringResource(id = R.string.str_already_registered),
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
            value = userDetails.name,
            onValueChange = { onValueChange(userDetails.copy(name = it)) },
            icon = Icons.Default.Person
        )
        CustomTextField(
            label = stringResource(id = R.string.str_email),
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth(0.8f),
            value = userDetails.email,
            onValueChange = { onValueChange(userDetails.copy(email = it)) },
            icon = Icons.Default.Email,
            keyboardType = KeyboardType.Email,
        )
        CustomTextField(
            label = stringResource(id = R.string.str_password),
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth(0.8f),
            value = userDetails.password,
            onValueChange = { onValueChange(userDetails.copy(password = it)) },
            icon = Icons.Default.Lock,
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation()
        )
        CustomTextField(
            label = stringResource(id = R.string.str_repeat_password),
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth(0.8f),
            value = userDetails.repeatPassword,
            onValueChange = { onValueChange(userDetails.copy(repeatPassword = it)) },
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation(),
            icon = Icons.Default.Lock
        )
        CustomTextField(
            label = stringResource(id = R.string.str_address),
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth(0.8f),
            value = userDetails.address,
            onValueChange = { onValueChange(userDetails.copy(address = it)) },
            icon = Icons.Default.Home
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = termsAccepted,
                onCheckedChange = { onTermsAcceptedChange(!termsAccepted) }
            )
            Text(
                text = stringResource(id = R.string.str_terms_agree),
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        Button(
            enabled = isFieldsNotEmpty,
            onClick = onRegisterClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            )
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
                    SpanStyle(
                        color = CustomRed,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
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