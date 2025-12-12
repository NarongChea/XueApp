package com.example.xueapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xueapp.data.VerifyOtpRequest
import com.example.xueapp.network.ApiService
import com.example.xueapp.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VerifyOtpViewModel(context: Context) : ViewModel() {

    private val _verifyOtpState = MutableStateFlow<VerifyOtpState>(VerifyOtpState.Idle)
    val verifyOtpState: StateFlow<VerifyOtpState> = _verifyOtpState

    private val apiService: ApiService = RetrofitInstance.create(context)

    fun verifyOtp(email: String, code: String) {
        viewModelScope.launch {
            _verifyOtpState.value = VerifyOtpState.Loading
            try {
                val response = apiService.verifyOtp(VerifyOtpRequest(email, code))
                if (response.status == "success") {
                    _verifyOtpState.value = VerifyOtpState.Success(response.message ?: "OTP Verified Successfully")
                } else {
                    _verifyOtpState.value = VerifyOtpState.Error(response.message ?: "OTP Verification Failed")
                }
            } catch (e: Exception) {
                _verifyOtpState.value = VerifyOtpState.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }
}

sealed class VerifyOtpState {
    object Idle : VerifyOtpState()
    object Loading : VerifyOtpState()
    data class Success(val message: String) : VerifyOtpState()
    data class Error(val message: String) : VerifyOtpState()
}
