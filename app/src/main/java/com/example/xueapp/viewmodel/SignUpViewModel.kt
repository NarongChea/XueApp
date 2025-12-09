package com.example.xueapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xueapp.data.AuthRequest
import com.example.xueapp.network.ApiService
import com.example.xueapp.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(context: Context) : ViewModel() {

    private val _signUpState = MutableStateFlow<SignUpState>(SignUpState.Idle)
    val signUpState: StateFlow<SignUpState> = _signUpState

    private val apiService: ApiService = RetrofitInstance.create(context)

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _signUpState.value = SignUpState.Loading
            try {
                val response = apiService.register(AuthRequest(email, password))
                if (response.status == "success") {
                    _signUpState.value = SignUpState.Success(response.message ?: "Registration Successful")
                } else {
                    _signUpState.value = SignUpState.Error(response.message ?: "Registration failed. Please try again.")
                }
            } catch (e: Exception) {
                _signUpState.value = SignUpState.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }
}

sealed class SignUpState {
    object Idle : SignUpState()
    object Loading : SignUpState()
    data class Success(val message: String) : SignUpState()
    data class Error(val message: String) : SignUpState()
}
