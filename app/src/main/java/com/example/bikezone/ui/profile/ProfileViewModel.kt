package com.example.bikezone.ui.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bikezone.data.cartItems.CartRepository
import com.example.bikezone.data.orders.OrderRepository
import com.example.bikezone.data.users.User
import com.example.bikezone.data.users.UserDetails
import com.example.bikezone.data.users.UserRepository
import com.example.bikezone.data.users.toUser
import com.example.bikezone.data.users.toUserDetails
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
) : ViewModel() {

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

    fun updateUiState(
        userDetails: UserDetails = profileUiState.userDetails,
    ) {
        profileUiState = profileUiState.copy(userDetails = userDetails)

    }

    suspend fun updateUser() {
        userRepository.update(profileUiState.userDetails.toUser())
    }

    suspend fun deleteUser() {
        cartRepository.deleteAllItems(cartRepository.getAllCartItemsStream(profileUiState.userDetails.id).first())
        orderRepository.deleteAllByUserId(profileUiState.userDetails.id)
        userRepository.delete(profileUiState.userDetails.toUser())
    }

    fun isNotEmpty(userDetails: UserDetails = profileUiState.userDetails): Boolean {
        return with(userDetails) {
            name.isNotBlank() && email.isNotBlank() && address.isNotBlank() && password.isNotBlank()
        }
    }

    suspend fun verifyOperation() {
        val user = userRepository.getUserByEmailStream(profileUiState.userDetails.email).first()
        profileUiState = if (user == null || user.auth) {
            profileUiState.copy(successFullUpdate = true, isNotSame = true)
        } else {
            profileUiState.copy(successFullUpdate = false, isNotSame = false)
        }

    }
}
data class ProfileUiState(
    val userDetails: UserDetails = UserDetails(),
    val successFullUpdate: Boolean = false,
    val isNotSame: Boolean = true
)

fun User.toProfileUiState(): ProfileUiState = ProfileUiState(
    userDetails = this.toUserDetails()
)
