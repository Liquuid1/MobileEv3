package com.example.snkr_app.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.snkr_app.viewmodels.NewsletterViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsletterView(vm: NewsletterViewModel = viewModel(), navController: NavController,) {
    val state by vm.state.collectAsState()
    val errors by vm.errors.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }



    val gradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
            MaterialTheme.colorScheme.surface
        )
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(brush = gradient)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Formulario de Registro SNKR",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Completa tus datos para recibir noticias, beneficios y acceso anticipado.",
                style = MaterialTheme.typography.bodyMedium
            )

            // 🧾 Nombre completo
            OutlinedTextField(
                value = state.nombre,
                onValueChange = vm::onNombreChange,
                label = { Text("Nombre completo") },
                isError = errors.nombre != null,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            AnimatedVisibility(errors.nombre != null, enter = fadeIn(), exit = fadeOut()) {
                Text(errors.nombre ?: "", color = MaterialTheme.colorScheme.error)
            }

            // ✉️ Email
            OutlinedTextField(
                value = state.email,
                onValueChange = vm::onEmailChange,
                label = { Text("Correo electrónico") },
                isError = errors.email != null,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            AnimatedVisibility(errors.email != null, enter = fadeIn(), exit = fadeOut()) {
                Text(errors.email ?: "", color = MaterialTheme.colorScheme.error)
            }

            // 🎂 Edad
            OutlinedTextField(
                value = state.edad,
                onValueChange = vm::onEdadChange,
                label = { Text("Edad") },
                isError = errors.edad != null,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            AnimatedVisibility(errors.edad != null, enter = fadeIn(), exit = fadeOut()) {
                Text(errors.edad ?: "", color = MaterialTheme.colorScheme.error)
            }

            // ✅ Acepta términos
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = state.aceptaTerminos,
                    onCheckedChange = vm::onTerminosChange
                )
                Text("Acepto los términos y condiciones")
            }
            AnimatedVisibility(errors.terminos != null, enter = fadeIn(), exit = fadeOut()) {
                Text(errors.terminos ?: "", color = MaterialTheme.colorScheme.error)
            }

            // 🔔 Suscripción
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(
                    checked = state.suscripcion,
                    onCheckedChange = vm::onSuscripcionChange
                )
                Text("Deseo recibir novedades y ofertas exclusivas")
            }
            AnimatedVisibility(errors.suscripcion != null, enter = fadeIn(), exit = fadeOut()) {
                Text(errors.suscripcion ?: "", color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 🚀 Botones de acción
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        if (vm.validate()) {
                            scope.launch {
                                snackbarHostState.showSnackbar("✅ Registro enviado con éxito.")
                            }
                            vm.reset()
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar("❌ Revisa los campos obligatorios.")
                            }
                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Suscribirse")
                }

                OutlinedButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Volver")
                }
            }
        }
    }
}
