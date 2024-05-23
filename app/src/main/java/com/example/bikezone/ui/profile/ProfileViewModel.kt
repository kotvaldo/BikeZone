package com.example.bikezone.ui.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bikezone.data.users.User
import com.example.bikezone.data.users.UserDetails
import com.example.bikezone.data.users.UserRepository
import com.example.bikezone.data.users.toUser
import com.example.bikezone.data.users.toUserDetails
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class ProfileUiState(
    val userDetails: UserDetails = UserDetails(),
    val successFullUpdate: Boolean = false,
    val isNotSame: Boolean = true
)

fun User.toProfileUiState(): ProfileUiState = ProfileUiState(
    userDetails = this.toUserDetails()
)
class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    /**
     * Holds current user ui state
     */
    var profileUiState by mutableStateOf(ProfileUiState())
        private set

    init {
        viewModelScope.launch {
            profileUiState = userRepository.getAuthUserStream(true)
                .filterNotNull()  // Filtrovať len nenulové hodnoty
                .first()
                .toProfileUiState()
        }
    }

    fun updateUiState(userDetails: UserDetails = profileUiState.userDetails, successFullUpdate: Boolean = false, isNotSame: Boolean = true) {
        profileUiState = ProfileUiState(userDetails = userDetails,successFullUpdate = successFullUpdate, isNotSame = isNotSame)
    }

    suspend fun updateUser() {
        userRepository.updateItem(profileUiState.userDetails.toUser())
    }

    suspend fun deleteUser() {
        userRepository.deleteItem(profileUiState.userDetails.toUser())
    }

    fun isNotEmpty(userDetails: UserDetails = profileUiState.userDetails) :Boolean {
        return with(userDetails) {
            name.isNotBlank() && email.isNotBlank() && address.isNotBlank() && password.isNotBlank()
        }
    }

    fun verifyOperation() {
        viewModelScope.launch {
            userRepository.getUserByEmailStream(profileUiState.userDetails.email).collect { user ->
                profileUiState = if (user != null) {
                    profileUiState.copy(isNotSame = false, successFullUpdate = false)
                } else {
                    profileUiState.copy(successFullUpdate = true, isNotSame = true)
                }
            }
        }
    }

}