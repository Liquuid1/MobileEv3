package com.example.snkr_app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snkr_app.data.models.Zapatilla
import com.example.snkr_app.data.repositories.ZapatillaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class ZapatillaListViewModel : ViewModel() {

    // Filtros
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _selectedTalla = MutableStateFlow<String?>(null)
    val selectedTalla = _selectedTalla.asStateFlow()

    private val _selectedColor = MutableStateFlow<String?>(null)
    val selectedColor = _selectedColor.asStateFlow()

    // Opciones para los desplegables
    val tallas: StateFlow<List<String>> = ZapatillaRepository.getZapatillas()
        .combine(searchText) { zapatillas, text ->
            listOf("Todas") + zapatillas.map { it.talla.toString() }.distinct()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val colores: StateFlow<List<String>> = ZapatillaRepository.getZapatillas()
        .combine(searchText) { zapatillas, text ->
            listOf("Todos") + zapatillas.map { it.color }.distinct()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Lógica de filtrado combinado
    val zapatillas: StateFlow<List<Zapatilla>> = combine(
        ZapatillaRepository.getZapatillas(),
        searchText,
        selectedTalla,
        selectedColor
    ) { zapatillas, text, talla, color ->
        var filteredList = zapatillas

        if (text.isNotBlank()) {
            filteredList = filteredList.filter {
                it.nombre.contains(text, ignoreCase = true) ||
                        it.marca.contains(text, ignoreCase = true)
            }
        }

        if (talla != null && talla != "Todas") {
            filteredList = filteredList.filter { it.talla.toString() == talla }
        }

        if (color != null && color != "Todos") {
            filteredList = filteredList.filter { it.color == color }
        }

        filteredList
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onTallaChange(talla: String) {
        _selectedTalla.value = if (talla == "Todas") null else talla
    }

    fun onColorChange(color: String) {
        _selectedColor.value = if (color == "Todos") null else color
    }
}