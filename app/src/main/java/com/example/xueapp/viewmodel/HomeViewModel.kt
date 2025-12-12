package com.example.xueapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xueapp.data.User
import com.example.xueapp.network.ApiService
import com.example.xueapp.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(context: Context) : ViewModel() {

    private val _userState = MutableStateFlow<UserState>(UserState.Loading)
    val userState: StateFlow<UserState> = _userState

    private val apiService: ApiService = RetrofitInstance.create(context)

    init {
        fetchUser()
    }

    fun fetchUser() {
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                val response = apiService.getMe()
                if (response.status == "success" && response.data?.user != null) {
                    _userState.value = UserState.Success(response.data.user)
                } else {
                    _userState.value = UserState.Error(response.message ?: "Failed to fetch user data")
                }
            } catch (e: Exception) {
                 _userState.value = UserState.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }
}

sealed class UserState {
    object Loading : UserState()
    data class Success(val user: User) : UserState()
    data class Error(val message: String) : UserState()
}
