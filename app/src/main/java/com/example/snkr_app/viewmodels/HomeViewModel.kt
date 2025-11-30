package com.example.snkr_app.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel: ViewModel() {
    private val _texto = MutableStateFlow("Home")
    val texto: StateFlow<String> = _texto
}
