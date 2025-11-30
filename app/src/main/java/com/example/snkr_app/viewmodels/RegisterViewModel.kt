package com.example.snkr_app.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snkr_app.auth.model.LoginResponse
import com.example.snkr_app.auth.model.Register
import com.example.snkr_app.auth.model.User
import com.example.snkr_app.auth.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(): ViewModel() {

    private val _registerStatus = MutableStateFlow<User?>(null)
    val registerStatus : StateFlow<User?> = _registerStatus

    private val _errorStatus = MutableStateFlow<String?>(null)
    val errorStatus: StateFlow<String?> = _errorStatus

    val repository = LoginRepository()

    fun register(name:String,email:String,password:String) {
        viewModelScope.launch {
            try {
                val loginResponse = repository.doRegister(name,email,password)
                Log.i("welcome", loginResponse.toString())
                val user = repository.getUser(loginResponse.authToken)
                _registerStatus.value = user
            } catch (e: Exception) {
                _errorStatus.value = e.message
                Log.e("welcome",e.toString())
            }
        }
    }


}