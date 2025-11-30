package com.example.snkr_app.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.snkr_app.data.models.Zapatilla
import com.example.snkr_app.data.repositories.ZapatillaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AdminPanelViewModel : ViewModel() {

    // Fuente de datos: lista reactiva del repository
    val zapatillas: StateFlow<List<Zapatilla>> = ZapatillaRepository.getZapatillas()

    // Campos del formulario
    var nombre by mutableStateOf("")
    var marca by mutableStateOf("")
    var talla by mutableStateOf("")
    var color by mutableStateOf("")
    var precio by mutableStateOf("")
    var fotoUrl by mutableStateOf("@drawable/zap.webp")
    var editingId: String? = null
    var searchQuery by mutableStateOf("")

    fun cargarParaEdicion(z: Zapatilla) {
        editingId = z.id
        nombre = z.nombre
        marca = z.marca
        talla = z.talla.toString()
        color = z.color
        precio = z.precio
        fotoUrl = z.fotoUrl
    }

    fun limpiarFormulario() {
        editingId = null
        nombre = ""
        marca = ""
        talla = ""
        color = ""
        precio = ""
        fotoUrl = ""
    }

    fun guardar() {
        val nueva = Zapatilla(
            id = editingId ?: System.currentTimeMillis().toString(),
            nombre = nombre,
            marca = marca,
            talla = talla.toDoubleOrNull() ?: 0.0,
            color = color,
            precio = precio,
            fotoUrl = fotoUrl
        )

        if (editingId == null) {
            ZapatillaRepository.addZapatilla(nueva)
        } else {
            ZapatillaRepository.updateZapatilla(nueva)
        }

        limpiarFormulario()
    }

    fun eliminar(id: String) {
        ZapatillaRepository.deleteZapatilla(id)
    }

    fun buscar(): List<Zapatilla> {
        val query = searchQuery.lowercase()
        return zapatillas.value.filter { z ->
            z.id.lowercase().contains(query) ||
                    z.nombre.lowercase().contains(query)
        }
    }
}

