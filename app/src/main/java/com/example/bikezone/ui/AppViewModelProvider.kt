package com.example.bikezone.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bikezone.AuthViewModel
import com.example.bikezone.BikeZoneApplication
import com.example.bikezone.ui.auth.LoginViewModel
import com.example.bikezone.ui.auth.RegisterViewModel
import com.example.bikezone.ui.home.HomeViewModel
import com.example.bikezone.ui.item.ItemDetailsViewModel
import com.example.bikezone.ui.profile.ProfileViewModel

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
        initializer {
            ProfileViewModel(application().container.userRepository)
        }
        initializer {
            HomeViewModel(application().container.itemRepository)
        }
        initializer {
            ItemDetailsViewModel(
                this.createSavedStateHandle(),
                application().container.itemRepository)
        }
    }
}

fun CreationExtras.application(): BikeZoneApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BikeZoneApplication)
