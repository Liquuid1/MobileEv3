package com.example.snkr_app.data.repositories

import com.example.snkr_app.data.models.Zapatilla
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Un repositorio Singleton para gestionar el estado del carrito de compras.
 */
object CartRepository {

    // Lista privada y mutable de los items en el carrito.
    private val _cartItems = MutableStateFlow<List<Zapatilla>>(emptyList())

    // Exposición pública y de solo lectura de los items del carrito.
    val cartItems = _cartItems.asStateFlow()

    /**
     * Añade una zapatilla a la lista actual de items en el carrito.
     */
    fun addToCart(zapatilla: Zapatilla) {
        // Creamos una nueva lista añadiendo la zapatilla para notificar a los observadores.
        _cartItems.value = _cartItems.value + zapatilla
    }

    /**
     * Elimina todos los items del carrito.
     */
    fun clearCart() {
        _cartItems.value = emptyList()
    }
}