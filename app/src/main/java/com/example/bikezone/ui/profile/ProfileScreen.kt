package com.example.bikezone.ui.profile

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.bikezone.BikeZoneBottomAppBar
import com.example.bikezone.BikeZoneTopAppBar
import com.example.bikezone.R
import com.example.bikezone.data.users.UserDetails
import com.example.bikezone.navigation.HomeDestination
import com.example.bikezone.navigation.LoginDestination
import com.example.bikezone.navigation.Routes
import com.example.bikezone.ui.AppViewModelProvider
import com.example.bikezone.ui.components.CustomTextField
import com.example.bikezone.ui.theme.BikeZoneTheme
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val coroutineScope = rememberCoroutineScope()
    var showSignOutDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    BikeZoneTheme {
        Scaffold(
            topBar = {
                BikeZoneTopAppBar(
                    title = R.string.str_profile,
                    canNavigateBack = true,
                    navigateBack = { navController.navigate(HomeDestination.route) },
                    modifier = Modifier
                )
            },
            bottomBar = {
                BikeZoneBottomAppBar(
                    navController = navController
                )
            },
            content = { innerPadding ->
                ProfileLayout(
                    contentPadding = innerPadding,
                    isFieldsNotEmpty = viewModel.isNotEmpty(),
                    userState = viewModel.profileUiState,
                    onValueChange = viewModel::updateUiState,
                    onSumbitClick = {
                        coroutineScope.launch {
                            viewModel.verifyOperation()
                            if (viewModel.profileUiState.successFullUpdate) {
                                viewModel.updateUser()
                            }
                        }
                    },
                    onDeleteClick = {
                        showDeleteDialog = true
                    },
                    onSignOutClick = {
                        showSignOutDialog = true
                    }
                )
                if (showDeleteDialog) {
                    SetupAlertDialog(
                        title = R.string.str_delete_account,
                        confirmMessage = R.string.str_want_to_delete,
                        accept = R.string.str_yes,
                        dismiss = R.string.str_no,
                        onDismiss = { showDeleteDialog = false },
                        onAccept = {
                            coroutineScope.launch {
                                viewModel.deleteUser()
                            }
                            navController.navigate(Routes.AuthRoute.route) {
                                navController.navigate(Routes.AuthRoute.route) {
                                    popUpTo(0) { inclusive = true }  // Clear the back stack
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
                if (showSignOutDialog) {
                    SetupAlertDialog(
                        title = R.string.str_sign_out,
                        confirmMessage = R.string.str_want_to_sign_out,
                        accept = R.string.str_yes,
                        dismiss = R.string.str_no,
                        onDismiss = { showSignOutDialog = false },
                        onAccept = {
                            coroutineScope.launch {
                                viewModel.updateUiState(
                                    userDetails = viewModel.profileUiState.userDetails.copy(
                                        auth = false
                                    )
                                )
                                viewModel.updateUser()
                                navController.navigate(LoginDestination.route) {
                                    popUpTo(LoginDestination.route) {
                                        inclusive = true
                                    }
                                }
                            }

                        }
                    )
                }

            }
        )
    }
}

@Composable
fun SetupAlertDialog(
    @StringRes
    title: Int,
    @StringRes
    confirmMessage: Int,
    @StringRes
    accept: Int,
    @StringRes
    dismiss: Int,
    onDismiss: () -> Unit,
    onAccept: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = stringResource(id = title)) },
        text = { Text(text = stringResource(id = confirmMessage)) },
        confirmButton = {
            TextButton(onClick = {
                onAccept()
            }) {
                Text(
                    text = stringResource(id = accept),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(
                    text = stringResource(id = dismiss),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileLayout(
    contentPadding: PaddingValues = PaddingValues(0.dp),
    userState: ProfileUiState,
    onValueChange: (UserDetails) -> Unit,
    onSumbitClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onSignOutClick: () -> Unit = {},
    isFieldsNotEmpty: Boolean
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = null,
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Fit
        )
        Text(
            text = stringResource(id = R.string.str_change_profile),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        AnimatedVisibility(visible = userState.successFullUpdate) {
            Text(
                text = stringResource(id = R.string.str_success_update),
                color = Color.Green,
            )
        }
        AnimatedVisibility(visible = !userState.isNotSame) {
            Text(
                text = stringResource(id = R.string.str_aldready_exist),
                color = MaterialTheme.colorScheme.error,
            )
        }
        CustomTextField(
            label = stringResource(id = R.string.str_name),
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth(0.8f),
            value = userState.userDetails.name,
            onValueChange = { onValueChange(userState.userDetails.copy(name = it)) },
            icon = Icons.Default.Person
        )
        CustomTextField(
            label = stringResource(id = R.string.str_email),
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth(0.8f),
            value = userState.userDetails.email,
            onValueChange = { onValueChange(userState.userDetails.copy(email = it)) },
            icon = Icons.Default.Email,
            keyboardType = KeyboardType.Email,
        )
        CustomTextField(
            label = stringResource(id = R.string.str_password),
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth(0.8f),
            value = userState.userDetails.password,
            onValueChange = { onValueChange(userState.userDetails.copy(password = it)) },
            icon = Icons.Default.Lock,
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation()
        )
        CustomTextField(
            label = stringResource(id = R.string.str_address),
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth(0.8f),
            value = userState.userDetails.address,
            onValueChange = { onValueChange(userState.userDetails.copy(address = it)) },
            icon = Icons.Default.Home
        )
        Button(
            enabled = isFieldsNotEmpty,
            onClick = onSumbitClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            )
        ) {
            Text(text = stringResource(id = R.string.str_submit_changes))
        }

        Spacer(modifier = Modifier.height(40.dp))
        Button(
            enabled = true,
            onClick = onSignOutClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            )
        ) {
            Text(text = stringResource(id = R.string.str_sign_out))
        }
        Button(
            enabled = true,
            onClick = onDeleteClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            ),
        ) {
            Text(text = stringResource(id = R.string.str_delete_account))
        }
    }
}