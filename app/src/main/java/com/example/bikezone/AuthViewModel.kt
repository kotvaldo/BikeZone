package com.example.bikezone

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bikezone.data.users.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {
    var userWithAuth by mutableStateOf(false)
        private set

    fun checkUserWithAuth() {
        viewModelScope.launch {
            userRepository.getAuthUserStream(true).collect { user ->
                userWithAuth = user != null
            }
        }
    }
}