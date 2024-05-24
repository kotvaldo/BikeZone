package com.example.bikezone.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bikezone.data.users.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class LoginState(
    val isWrongField: Boolean = false,
    val isAuthenticated: Boolean = false,
    val doesExist:Boolean = true
)

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _loginUiState = MutableStateFlow(LoginState())
    var loginUiState: StateFlow<LoginState> = _loginUiState.asStateFlow()

    var emailInput by mutableStateOf("")
        private set
    var passwordInput by mutableStateOf("")
        private set


    fun updateEmail(email: String) {
        emailInput = email
    }

    fun updatePassword(password: String) {
        passwordInput = password
    }

    fun verifyAndLogin() {
        if (emailInput.isNotEmpty() && passwordInput.isNotEmpty()) {
            viewModelScope.launch {
                userRepository.getUserByEmailAndPasswordStream(emailInput, passwordInput)
                    .firstOrNull()?.let { currentUser ->
                        val updatedUser = currentUser.copy(auth = true)
                        userRepository.updateItem(updatedUser)
                        _loginUiState.update { currentState ->
                            currentState.copy(
                                isAuthenticated = true,
                                isWrongField = false,
                                doesExist = true
                            )
                        }
                    } ?: run {
                    _loginUiState.update { currentState -> currentState.copy(isWrongField = true, doesExist = false) }
                }
            }
        } else {
            _loginUiState.update { currentState -> currentState.copy(isWrongField = true) }
        }
    }


}