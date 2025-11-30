package com.example.snkr_app.auth.repository

import com.example.snkr_app.auth.model.Login
import com.example.snkr_app.auth.remote.RetrofitInstance
import com.example.snkr_app.auth.model.LoginResponse
import com.example.snkr_app.auth.model.Register
import com.example.snkr_app.auth.model.User

class LoginRepository {
    suspend fun doLogin(email: String, password: String): LoginResponse {
        val login = Login(email, password)
        return RetrofitInstance.api.doLogin(login)
    }

    suspend fun getUser(token: String): User {
        return RetrofitInstance.api.getUser("Bearer $token")
    }

    suspend fun doRegister(name:String, email:String, password: String): LoginResponse{
        val register = Register(name,email,password)
        return RetrofitInstance.api.doRegister(register)
    }
}