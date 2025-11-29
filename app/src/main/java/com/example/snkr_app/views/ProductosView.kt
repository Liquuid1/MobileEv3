package com.example.snkr_app.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.snkr_app.navigation.Route
import com.example.snkr_app.viewmodels.ProductosViewModel
import kotlinx.coroutines.launch

/**
 * CLASE 2: Flujo y Navegación
 * - Lista con navegación a Detalle con argumento (id).
 * - Uso de NavController.navigate con launchSingleTop y popUpTo.
 * - Snackbar para feedback al tocar un ítem.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosView(
    navController: NavController,
    vm: ProductosViewModel = viewModel()
) {
    val items = vm.items.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                "Navegación: Lista → Detalle",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Toca un elemento para navegar con argumento. Observa cómo vuelve con Back.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(items) { index, item ->
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Abriendo detalle de $item")
                                }
                                navController.navigate(Route.ProductosDetail.build(id = (index + 1).toString())) {
                                    launchSingleTop = true
                                    // Ejemplo de control de back stack:
                                    popUpTo(Route.Productos.route) { inclusive = false }
                                }
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(item, style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.weight(1f))
                            Text("Ver detalle", style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }
            }
        }
    }
}

/**
 * Pantalla de Detalle para la navegación con argumento
 */
@Composable
fun ProductosDetailView(
    id: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Detalle", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("ID recibido en la ruta: $id", style = MaterialTheme.typography.bodyLarge)
        Button(onClick = onBack) { Text("Volver") }
    }
}

