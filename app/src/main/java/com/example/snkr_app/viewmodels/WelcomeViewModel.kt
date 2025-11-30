package com.example.snkr_app.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.snkr_app.auth.repository.LoginRepository
import com.example.snkr_app.auth.model.LoginResponse
import com.example.snkr_app.auth.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import android.util.Log
import com.example.snkr_app.auth.UserPreferences

class WelcomeViewModel() : ViewModel() {

    private val _titulo = MutableStateFlow<String>("Bienvenidos")
    val titulo: StateFlow<String> = _titulo
    private val _loginState = MutableStateFlow<User?>(null)
    val loginState: StateFlow<User?> = _loginState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    val repository = LoginRepository()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val loginResponse = repository.doLogin(email, password)
                val user = repository.getUser(loginResponse.authToken)
                _loginState.value = user
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }
}
