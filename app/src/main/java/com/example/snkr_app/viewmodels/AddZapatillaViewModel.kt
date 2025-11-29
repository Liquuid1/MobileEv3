package com.example.snkr_app.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.snkr_app.data.models.Zapatilla
import com.example.snkr_app.data.repositories.ZapatillaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

data class AddZapatillaState(
    val nombre: String = "",
    val marca: String = "",
    val talla: String = "",
    val color: String = "",
    val precio: String = "",
    val fotoBitmap: Bitmap? = null, // Almacenamos el Bitmap directamente
    val isSaved: Boolean = false
)

class AddZapatillaViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AddZapatillaState())
    val uiState = _uiState.asStateFlow()

    fun onNombreChange(value: String) {
        _uiState.value = _uiState.value.copy(nombre = value)
    }

    fun onMarcaChange(value: String) {
        _uiState.value = _uiState.value.copy(marca = value)
    }

    fun onTallaChange(value: String) {
        _uiState.value = _uiState.value.copy(talla = value)
    }

    fun onColorChange(value: String) {
        _uiState.value = _uiState.value.copy(color = value)
    }

    fun onPrecioChange(value: String) {
        _uiState.value = _uiState.value.copy(precio = value)
    }

    fun onFotoChange(bitmap: Bitmap?) {
        _uiState.value = _uiState.value.copy(fotoBitmap = bitmap)
    }

    fun saveZapatilla() {
        val state = _uiState.value
        if (state.nombre.isBlank() || state.marca.isBlank() || state.talla.isBlank() || state.color.isBlank() || state.precio.isBlank()) {
            return
        }

        val newZapatilla = Zapatilla(
            id = UUID.randomUUID().toString(),
            nombre = state.nombre,
            marca = state.marca,
            talla = state.talla.toDoubleOrNull() ?: 0.0,
            color = state.color,
            precio = "CLP $${state.precio}",
            // Como no guardamos la foto, usamos un placeholder
            fotoUrl = "@drawable/zap"
        )

        ZapatillaRepository.addZapatilla(newZapatilla)
        _uiState.value = _uiState.value.copy(isSaved = true)
    }
}
