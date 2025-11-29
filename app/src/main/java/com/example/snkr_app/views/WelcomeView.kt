package com.example.snkr_app.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.snkr_app.viewmodels.WelcomeViewModel
import com.example.snkr_app.R

@Composable
fun WelcomeView(
    onStartClick: () -> Unit,
    vm: WelcomeViewModel = viewModel()
) {
    val titulo = vm.titulo.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF000000),
                        Color(0xFF1C1C1C)
                    )
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
                text = titulo.ifBlank { "Bienvenido a SNKRHOOD" },
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Subtítulo
            Text(
                text = "Encuentra las zapatillas más exclusivas y únete a la comunidad sneakerhead.",
                color = Color(0xFFBBBBBB),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Botón de inicio
            Button(
                onClick = onStartClick,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00E676)
                ),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp)
            ) {
                Text(
                    text = "Explorar SNKRHOOD",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
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
