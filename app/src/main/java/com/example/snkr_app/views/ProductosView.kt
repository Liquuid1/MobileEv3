package com.example.snkr_app.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.snkr_app.R.drawable.zap
import com.example.snkr_app.data.models.Zapatilla
import com.example.snkr_app.data.repositories.ZapatillaRepository
import com.example.snkr_app.navigation.Route
import com.example.snkr_app.viewmodels.ProductosViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosView(
    navController: NavController,
    vm: ProductosViewModel = viewModel()
) {
    val zapatillas = ZapatillaRepository.getZapatillas().collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                "Todos los modelos",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Explora nuestra colección completa de zapatillas",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(16.dp))

            // Mostrar productos en tarjetas: 2 por fila
            val rows = remember(zapatillas) { zapatillas.chunked(2) }
            rows.forEachIndexed { rowIndex, rowList ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowList.forEachIndexed { colIndex, product ->
                        val productIndex = rowIndex * 2 + colIndex
                        ProductCard(
                            product = product,
                            modifier = Modifier.weight(1f),
                            onVerMas = {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Abriendo detalle de ${product.nombre}")
                                }
                                navController.navigate(Route.ProductosDetail.build(id = product.id)) {
                                    launchSingleTop = true
                                    popUpTo(Route.Productos.route) { inclusive = false }
                                }
                            }
                        )
                    }
                    // si la fila tiene 1 item, agregamos un spacer para balancear
                    if (rowList.size == 1) Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun ProductCard(
    product: Zapatilla,
    modifier: Modifier = Modifier,
    onVerMas: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(zap),
                contentDescription = product.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp)
                    )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = product.nombre, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = product.precio, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onVerMas) {
                    Text("Ver más")
                }
            }
        }
    }
}
