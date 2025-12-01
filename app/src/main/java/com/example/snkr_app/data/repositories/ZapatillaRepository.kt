package com.example.snkr_app.data.repositories

import kotlinx.coroutines.flow.MutableStateFlow
import com.example.snkr_app.data.models.Zapatilla
import kotlinx.coroutines.flow.StateFlow

object ZapatillaRepository {

    private val initialZapatillas = listOf(
        Zapatilla("1", "Air Nova 3000", "Nike", 9.5, "Rojo", "CLP $89.990", "@drawable/zap"),
        Zapatilla("2", "Runner Cloud X", "Adidas", 10.0, "Azul", "CLP $74.500", "@drawable/zap"),
        Zapatilla("3", "Street Glide 2", "Puma", 8.0, "Negro", "CLP $99.990", "@drawable/zap"),
        Zapatilla("4", "Trail King", "Reebok", 11.5, "Gris", "CLP $64.990", "@drawable/zap")
    )

    private val _zapatillas = MutableStateFlow<List<Zapatilla>>(initialZapatillas)

    val zapatillas = _zapatillas

    fun getZapatillas(): StateFlow<List<Zapatilla>> = zapatillas

    fun addZapatilla(zapatilla: Zapatilla) {
        zapatillas.value = zapatillas.value + zapatilla
    }

    fun deleteZapatilla(id: String) {
        zapatillas.value = zapatillas.value.filter { it.id != id }
    }

    fun updateZapatilla(z: Zapatilla) {
        zapatillas.value = zapatillas.value.map {
            if (it.id == z.id) z else it
        }
    }

    fun getById(id: String): Zapatilla? {
        return zapatillas.value.find { it.id == id }
    }

    // Esta función se añade para que las pruebas unitarias puedan limpiar el estado.
    fun clear() {
        _zapatillas.value = initialZapatillas
    }
}
