package com.example.bikezone.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bikezone.data.users.User
import com.example.bikezone.data.users.UserRepository
import kotlinx.coroutines.flow.first

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
    val alreadyExist: Boolean = false,
    val termsAccepted: Boolean = false,
    val isPasswordSame: Boolean = true,
    val isFieldsNotEmpty: Boolean = false,
    val userDetails: RegisterUserDetails = RegisterUserDetails()
)

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    var registerUiState by mutableStateOf(RegisterState())
        private set

    fun updateUiState(registerState: RegisterState) {
        registerUiState = registerState
    }
    fun updateRegisterDetails(registerDetails: RegisterUserDetails) {
        registerUiState = registerUiState.copy(userDetails = registerDetails, isPasswordSame = this.verifySamePasswds(registerDetails), isFieldsNotEmpty = verifyIsFieldsNotEmpty(registerDetails))
    }

    fun updateStateTermsAccept(termsAccepted: Boolean) {
        registerUiState = RegisterState(
            userDetails = registerUiState.userDetails,
            termsAccepted = termsAccepted,
            isPasswordSame = registerUiState.isPasswordSame,
            alreadyExist = registerUiState.alreadyExist,
            isFieldsNotEmpty = registerUiState.isFieldsNotEmpty
        )
    }

     suspend fun verifyAlreadyExistAccount() {

        val user = userRepository.getUserByEmailStream(registerUiState.userDetails.email).first()
        if (user != null) {
            registerUiState = registerUiState.copy(alreadyExist = true)
        }

    }

    suspend fun saveItem() {
        userRepository.insert(registerUiState.userDetails.toUser())
    }

    private fun verifySamePasswds(registerDetails: RegisterUserDetails = registerUiState.userDetails): Boolean {
        return with(registerDetails) {
            password == repeatPassword
        }
    }

    fun verifyTermsAccepted(): Boolean {
        return registerUiState.termsAccepted
    }

    fun verifyIsFieldsNotEmpty(registerDetails: RegisterUserDetails = registerUiState.userDetails): Boolean {
        return with(registerDetails) {
            name.isNotBlank() && address.isNotBlank() && password.isNotBlank() && repeatPassword.isNotBlank() && email.isNotBlank()
        }
    }
}

fun RegisterUserDetails.toUser(): User = User(
    id = id,
    name = name,
    email = email,
    password = password,
    address = address,
    auth = auth
)