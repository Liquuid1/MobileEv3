package com.example.snkr_app.auth.model

data class User(
    val id: Int,
    val created_at : String,
    val name: String,
    val email: String,
    val role_id: Int
)