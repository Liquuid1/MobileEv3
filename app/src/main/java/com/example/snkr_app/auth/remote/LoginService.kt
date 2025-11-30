package com.example.snkr_app.auth.remote

import com.example.snkr_app.auth.model.Login
import com.example.snkr_app.auth.model.LoginResponse
import com.example.snkr_app.auth.model.Register
import com.example.snkr_app.auth.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginService {
    @POST("auth/login")
    suspend fun doLogin(@Body login: Login): LoginResponse

    @GET("auth/me")
    suspend fun getUser(@Header("Authorization") token: String): User

    @POST("auth/signup")
    suspend fun doRegister(@Body register: Register): LoginResponse
}
