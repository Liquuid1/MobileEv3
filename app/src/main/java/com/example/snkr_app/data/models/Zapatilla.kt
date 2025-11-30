package com.example.snkr_app.data.models

data class Zapatilla(
    val id: String,
    val nombre: String,
    val marca: String,
    val talla: Double,
    val color: String,
    val precio: String,
    val fotoUrl: String // Por ahora, usaremos URLs o Ids de recursos drawable
)
