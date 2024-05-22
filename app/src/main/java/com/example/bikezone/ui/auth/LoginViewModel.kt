package com.example.bikezone.ui.auth

import androidx.lifecycle.ViewModel

data class LoginState (
    val loginDetails: LoginDetails = LoginDetails(),
    val isEntryValid: Boolean,
    )
data class LoginDetails (
    val email:String = "",
    val password:String = "",
)
class LoginViewModel() : ViewModel() {

}