package com.example.xueapp.network

import com.example.xueapp.data.AuthRequest
import com.example.xueapp.data.AuthResponse
import com.example.xueapp.data.VerifyOtpRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("api/v1/auth/register")
    suspend fun register(@Body request: AuthRequest): AuthResponse

    @POST("api/v1/auth/verify/email")
    suspend fun verifyOtp(@Body request: VerifyOtpRequest): AuthResponse

    @POST("api/v1/auth/login")
    suspend fun login(@Body request: AuthRequest): AuthResponse

    @GET("api/v1/auth/me")
    suspend fun getMe(): AuthResponse
}
