package com.example.snkr_app.views

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.snkr_app.viewmodels.WelcomeViewModel
import com.example.snkr_app.R
import kotlinx.coroutines.delay

@Composable
fun WelcomeView(
    onNavigateHome: () -> Unit,
    onNavigateRegister: () -> Unit, // 👈 nuevo callback para ir al registro
    vm: WelcomeViewModel = viewModel()
) {

    val titulo = vm.titulo.collectAsState().value

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val userState = vm.loginState.collectAsState()
    val errorState = vm.errorState.collectAsState()

    LaunchedEffect(userState.value) {
        if (userState.value != null) {
            try {
                onNavigateHome()
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
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF000000), Color(0xFF1C1C1C))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Logo o imagen principal
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "SNKRHOOD Logo",
                modifier = Modifier
                    .size(160.dp)
                    .padding(bottom = 32.dp),
                contentScale = ContentScale.Fit
            )

            // Título principal
            Text(
                text = titulo,
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(0.85f),
                textStyle = LocalTextStyle.current.copy(color = Color.White)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(0.85f),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val iconLabel = if (passwordVisible) "Ocultar" else "Mostrar"
                    TextButton(onClick = { passwordVisible = !passwordVisible }) {
                        Text(iconLabel, color = Color(0xFF00E676))
                    }
                },
                textStyle = LocalTextStyle.current.copy(color = Color.White)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botón de inicio
            Button(
                onClick = { vm.login(email, password) },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E676)),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp)
            ) {
                Text(
                    text = "Iniciar Sesión",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 👇 Nuevo botón de registro
            OutlinedButton(
                onClick = { onNavigateRegister() },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
            ) {
                Text(
                    text = "Registrarse",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            }

            AnimatedVisibility(
                visible = showError,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = "Hubo un error iniciando sesión, inténtelo de nuevo",
                    color = Color.Red,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Texto pequeño tipo lema
            Text(
                text = "🔥 Streetwear, cultura y estilo en un solo lugar.",
                color = Color(0xFF888888),
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
