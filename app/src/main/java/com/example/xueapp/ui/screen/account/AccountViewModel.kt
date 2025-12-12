package com.example.xueapp.ui.screen.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xueapp.model.User
import com.example.xueapp.network.ApiClient
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {

    var user by mutableStateOf<User?>(null)
        private set

    var logoutState by mutableStateOf<LogoutState>(LogoutState.Idle)
        private set

    fun getMe(token: String) {
        viewModelScope.launch {
            try {
                val response = ApiClient.userService.getMe("Bearer $token")
                if (response.isSuccessful) {
                    user = response.body()?.data?.user
                } else {
                    // Handle error
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

    fun logout(token: String) {
        viewModelScope.launch {
            logoutState = LogoutState.Loading
            try {
                val response = ApiClient.userService.logout("Bearer $token")
                if (response.isSuccessful) {
                    logoutState = LogoutState.Success
                } else {
                    logoutState = LogoutState.Error("Logout failed")
                }
            } catch (e: Exception) {
                logoutState = LogoutState.Error("An error occurred")
            }
        }
    }
}

sealed class LogoutState {
    object Idle : LogoutState()
    object Loading : LogoutState()
    object Success : LogoutState()
    data class Error(val message: String) : LogoutState()
}