package com.example.snkr_app.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snkr_app.data.repositories.CartRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartView() {
    val cartItems by CartRepository.cartItems.collectAsState()
    var showPaymentDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mi Carrito") })
        }
    ) { paddingValues ->
        if (cartItems.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Tu carrito está vacío", fontSize = 20.sp)
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)
            ) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(cartItems) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(item.nombre, fontWeight = FontWeight.Bold)
                                    Text(item.marca)
                                }
                                Text(item.precio)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { showPaymentDialog = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Pagar Ahora")
                }
            }
        }
    }

    if (showPaymentDialog) {
        AlertDialog(
            onDismissRequest = { showPaymentDialog = false },
            title = { Text("Confirmación") },
            text = { Text("Has pagado. ¡Gracias por tu compra!") },
            confirmButton = {
                Button(
                    onClick = {
                        showPaymentDialog = false
                        CartRepository.clearCart()
                        // Aquí podrías navegar a la pantalla de inicio si quieres
                    }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
}
