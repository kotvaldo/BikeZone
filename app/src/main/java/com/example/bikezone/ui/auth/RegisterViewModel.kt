package com.example.bikezone.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bikezone.data.users.UserRepository

data class RegisterUserDetails(
    val id: Int = 0,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val address: String = "",
    val auth: Boolean = false
)

data class RegisterState(
    val doesExist: Boolean = false,
    val termsAccepted: Boolean = false,
    val isPasswordSame: Boolean = true,
    val isFieldsNotEmpty: Boolean = false,
    val userDetails: RegisterUserDetails = RegisterUserDetails()
)

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    var registerUiState by mutableStateOf(RegisterState())
        private set

    fun updateUiState(registerUserDetails: RegisterUserDetails) {
        registerUiState = RegisterState(
            userDetails = registerUserDetails,
            termsAccepted = registerUiState.termsAccepted,
            isPasswordSame = verifySamePasswds(registerUserDetails),
            doesExist = registerUiState.doesExist,
            isFieldsNotEmpty = verifyIsFieldsNotEmpty(registerUserDetails)
        )
    }

    fun updateStateTermsAccept(termsAccepted: Boolean) {
        registerUiState = RegisterState(
            userDetails = registerUiState.userDetails,
            termsAccepted = !registerUiState.termsAccepted,
            isPasswordSame = registerUiState.isPasswordSame,
            doesExist = registerUiState.doesExist,
            isFieldsNotEmpty = registerUiState.isFieldsNotEmpty
        )
    }



    private fun verifySamePasswds(registerDetails: RegisterUserDetails = registerUiState.userDetails): Boolean {
        return with(registerDetails) {
            password == repeatPassword
        }
    }

    private fun verifyIsFieldsNotEmpty(registerDetails: RegisterUserDetails = registerUiState.userDetails): Boolean {
        return with(registerDetails) {
            name.isNotBlank() && address.isNotBlank() && password.isNotBlank() && repeatPassword.isNotBlank() && email.isNotBlank()
        }
    }
}