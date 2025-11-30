package com.example.snkr_app.views

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.snkr_app.viewmodels.RegisterViewModel
import kotlinx.coroutines.delay

@Composable
fun RegisterView(
    onRegisterSuccess: () -> Unit,
    onBack: () -> Unit,
    vm: RegisterViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val userState = vm.registerStatus.collectAsState()
    val errorState = vm.errorStatus.collectAsState()

    LaunchedEffect(userState.value) {
        if (userState.value != null) {
            try {
                onRegisterSuccess()
            } catch (e: Exception) {
                Log.e("welcome", e.toString())
            }
        }
    }

    var showError by remember { mutableStateOf(false) }
    LaunchedEffect(errorState.value) {
        if (errorState.value != null) {
            showError = true
            delay(3000)
            showError = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black), // 👈 fondo negro
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Formulario de Registro",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White // 👈 texto blanco para contraste
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { vm.register(name,email,password) }) {
                Text("Registrarse")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(onClick = { onBack() }) {
                Text("Volver")
            }

            AnimatedVisibility(
                visible = showError,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = "Hubo un error registrandose, inténtelo de nuevo",
                    color = Color.Red,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}
