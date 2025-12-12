package com.example.xueapp.model

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header

interface UserService {
    @GET("auth/me")
    suspend fun getMe(@Header("Authorization") token: String): Response<GetMeResponse>

    @DELETE("auth/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<LogoutResponse>
}

data class GetMeResponse(
    val status: String,
    val message: String,
    val data: UserData
)

data class UserData(
    val user: User
)

data class User(
    @SerializedName("_id") val id: String,
    val email: String,
    val isEmailVerified: Boolean,
    val roles: String,
    val createdAt: String,
    val updatedAt: String
)

data class LogoutResponse(
    val status: String,
    val message: String
)