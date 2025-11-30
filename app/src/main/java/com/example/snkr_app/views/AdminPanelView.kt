package com.example.snkr_app.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.snkr_app.viewmodels.AdminPanelViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@Composable
fun AdminPanelView(
    vm: AdminPanelViewModel = viewModel(),
    navController: NavController
) {
    val zapatillas = vm.zapatillas.collectAsState().value

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = if (vm.editingId == null) "Agregar Zapatilla" else "Editar Zapatilla",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(16.dp))

        // -------- FORMULARIO ---------
        OutlinedTextField(
            value = vm.nombre,
            onValueChange = { vm.nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = vm.marca,
            onValueChange = { vm.marca = it },
            label = { Text("Marca") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = vm.talla,
            onValueChange = { vm.talla = it },
            label = { Text("Talla") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = vm.color,
            onValueChange = { vm.color = it },
            label = { Text("Color") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = vm.precio,
            onValueChange = { vm.precio = it },
            label = { Text("Precio") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = { vm.guardar() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (vm.editingId == null) "Agregar" else "Actualizar")
        }

        if (vm.editingId != null) {
            TextButton(onClick = { vm.limpiarFormulario() }) {
                Text("Cancelar edición")
            }
        }

        OutlinedTextField(
            value = vm.searchQuery,
            onValueChange = { vm.searchQuery = it },
            label = { Text("Buscar por ID o nombre") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (vm.searchQuery.isNotEmpty()) {
                    IconButton(onClick = { vm.searchQuery = "" }) {
                        Icon(Icons.Default.Close, contentDescription = "Limpiar")
                    }
                }
            }
        )
        Spacer(Modifier.height(24.dp))

        Divider()

        Spacer(Modifier.height(16.dp))
        Text("Listado de Zapatillas", style = MaterialTheme.typography.titleMedium)

        Spacer(Modifier.height(8.dp))

        // -------- LISTADO FILTRADO ---------
        val zapatillasFiltradas = vm.buscar()

        zapatillasFiltradas.forEach { z ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(Modifier.padding(12.dp)) {
                    Text(z.nombre, fontWeight = FontWeight.Bold)
                    Text("ID: ${z.id}")
                    Text("Marca: ${z.marca}")
                    Text("Talla: ${z.talla}")
                    Text("Precio: ${z.precio}")

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { vm.cargarParaEdicion(z) }) {
                            Text("Editar")
                        }
                        TextButton(onClick = { vm.eliminar(z.id) }) {
                            Text(
                                "Eliminar",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}
