package com.example.snkr_app.auth.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: LoginService by lazy {
        Retrofit.Builder()
            .baseUrl("https://x8ki-letl-twmt.n7.xano.io/api:cRiGHljp/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginService::class.java)
    }
}