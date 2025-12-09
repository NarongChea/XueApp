package com.example.xueapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VerifyOtpViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VerifyOtpViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VerifyOtpViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
