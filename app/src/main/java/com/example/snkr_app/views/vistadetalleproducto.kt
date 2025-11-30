package com.example.snkr_app.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.snkr_app.R
import com.example.snkr_app.data.models.Zapatilla

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaDetalleProducto(zapatilla: Zapatilla, onAddToCart: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = zapatilla.nombre) })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.zap),
                contentDescription = "Foto de la zapatilla",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp)
                    )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Marca: ${zapatilla.marca}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Talla: ${zapatilla.talla}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Color: ${zapatilla.color}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Precio: ${zapatilla.precio}")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onAddToCart() }) {
                Text(text = "Agregar al carrito")
            }
        }
    }
}
