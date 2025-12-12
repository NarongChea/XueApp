package com.example.xueapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xueapp.data.AuthRequest
import com.example.xueapp.data.AuthResponse
import com.example.xueapp.network.ApiService
import com.example.xueapp.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModel(context: Context) : ViewModel() {

    private val _signInState = MutableStateFlow<SignInState>(SignInState.Idle)
    val signInState: StateFlow<SignInState> = _signInState

    private val apiService: ApiService = RetrofitInstance.create(context)

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _signInState.value = SignInState.Loading
            try {
                val response = apiService.login(AuthRequest(email, password))
                if (response.status == "success" && response.data?.tokens?.accessToken != null) {
                    _signInState.value = SignInState.Success(response)
                } else {
                    _signInState.value = SignInState.Error(response.message ?: "Login failed")
                }
            } catch (e: Exception) {
                _signInState.value = SignInState.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }
}

sealed class SignInState {
    object Idle : SignInState()
    object Loading : SignInState()
    data class Success(val authResponse: AuthResponse) : SignInState()
    data class Error(val message: String) : SignInState()
}
