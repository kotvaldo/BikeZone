package com.example.bikezone.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bikezone.BikeZoneApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {

    }
}

fun CreationExtras.bzApplication(): BikeZoneApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BikeZoneApplication)
