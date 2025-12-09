package com.example.xueapp.data

import com.google.gson.annotations.SerializedName

// Root object of the JSON response
data class AuthResponse(
    val status: String?,
    val message: String?,
    val data: AuthData?
)

// "data" object inside the response
data class AuthData(
    val user: User?,
    val tokens: Tokens?
)

// "user" object inside the data
data class User(
    val email: String?,
    @SerializedName("_id") // Handle underscore in field name
    val id: String?,
    val isEmailVerified: Boolean?,
    val roles: String?,
    val createdAt: String?,
    val updatedAt: String?
)

// "tokens" object inside the data
data class Tokens(
    val accessToken: String?,
    val refreshToken: String?
)
