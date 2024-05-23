package com.example.bikezone.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bikezone.AuthViewModel
import com.example.bikezone.BikeZoneApplication
import com.example.bikezone.ui.auth.LoginViewModel
import com.example.bikezone.ui.auth.RegisterViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            LoginViewModel(application().container.userRepository)
        }

        initializer {
            RegisterViewModel(application().container.userRepository)
        }

        initializer {
            AuthViewModel(application().container.userRepository)
        }
    }
}

fun CreationExtras.application(): BikeZoneApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BikeZoneApplication)
