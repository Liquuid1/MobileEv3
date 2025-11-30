package com.example.snkr_app.views

import androidx.compose.runtime.collectAsState
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Security
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.snkr_app.viewmodels.WelcomeViewModel
import com.example.snkr_app.R.drawable.zap
import com.example.snkr_app.data.models.Zapatilla
import kotlinx.coroutines.launch
import com.example.snkr_app.data.repositories.ZapatillaRepository

// --- Modelo simulado local para las tarjetas ---
private data class ShoeProduct(
    val name: String,
    val price: String
)



// --- Vista principal exportada ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    navController: NavController,
    vm: WelcomeViewModel = viewModel()
) {
    val titulo = vm.titulo.collectAsState().value

    // --- Lista simulada ---
    val zapatillas = ZapatillaRepository.getZapatillas().collectAsState().value

    val productosDestacados = zapatillas.take(4)
    // Scrollable column para toda la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(0.dp)
    ) {
        HeroSection(
            title = "Descubre tu próximo par",
            subtitle = "SNKR está en construcción, pero ya puedes explorar",
            onVerModelos = { navController.navigate("productos")  },
            onSuscribirse = { navController.navigate("newsletter") },
            onSubirZapatilla = { navController.navigate("addZapatilla")}
        )

        Spacer(modifier = Modifier.height(16.dp))

        MiniGallerySection(
            products = productosDestacados,
            onVerMas = { /* manejar ver mas (por ahora noop o snackbar) */ }
        )

        Spacer(modifier = Modifier.height(20.dp))

        BenefitsSection()

        Spacer(modifier = Modifier.height(20.dp))

        NewsletterSection(onSubscribe = { navController.navigate("newsletter") })

        Spacer(modifier = Modifier.height(20.dp))

        UploadTeaserSection(onUploadClick = { navController.navigate("addZapatilla") })

        Spacer(modifier = Modifier.height(40.dp))
    }
}

// -------------------- Hero Section --------------------
@Composable
private fun HeroSection(
    title: String,
    subtitle: String,
    onVerModelos: () -> Unit,
    onSuscribirse: () -> Unit,
    onSubirZapatilla: () -> Unit,
) {
    // ligera animación de aparición/scale en el título
    val appear by remember { androidx.compose.runtime.mutableStateOf(true) }
    val scale by animateFloatAsState(
        targetValue = if (appear) 1f else 0.98f,
        animationSpec = tween(durationMillis = 600)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 260.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.95f),
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.70f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .scale(scale)
        ) {
            Text(
                text = title,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
            )
            Spacer(modifier = Modifier.height(18.dp))

            // CTA Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = onVerModelos,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("Ver modelos")
                }
                OutlinedButton(
                    onClick = onSuscribirse,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text("Suscribirse a newsletter")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                onClick = onSubirZapatilla,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Subir tu zapatilla")
            }
        }
    }
}

// -------------------- Mini Gallery --------------------
@Composable
private fun MiniGallerySection(
    products: List<Zapatilla>,
    onVerMas: (Zapatilla) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "Modelos destacados",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // filas de tarjetas: 2 por fila (responsive)
        val rows = remember(products) { products.chunked(2) }
        rows.forEach { rowList ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowList.forEach { product ->
                    ProductCard(
                        product = product,
                        modifier = Modifier
                            .weight(1f),
                        onVerMas = { onVerMas(product) }
                    )
                }
                // si la fila tiene 1 item, agregamos un spacer para balancear
                if (rowList.size == 1) Spacer(modifier = Modifier.weight(1f))
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
            // Placeholder de imagen: caja con texto "Imagen"
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

// -------------------- Benefits --------------------
@Composable
private fun BenefitsSection() {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text("Beneficios", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BenefitItem(icon = Icons.Default.LocalShipping, text = "Envío rápido")
            BenefitItem(icon = Icons.Default.Security, text = "Garantía de autenticidad")
            BenefitItem(icon = Icons.Default.PhoneAndroid, text = "Compra desde tu móvil")
        }
    }
}

@Composable
private fun BenefitItem(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(100.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = text, tint = MaterialTheme.colorScheme.primary)
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = text, fontSize = 12.sp)
    }
}

// -------------------- Newsletter --------------------
@Composable
private fun NewsletterSection(onSubscribe: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Sé el primero en enterarte de lanzamientos y ofertas exclusivas.",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Promesas solo por email — nada real, es demo.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(onClick = onSubscribe) {
                Text("Suscribirme ahora")
            }
        }
    }
}

// -------------------- Upload teaser --------------------
@Composable
private fun UploadTeaserSection(onUploadClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // placeholder con silueta (emoji) + icono de cámara
            Box(
                modifier = Modifier
                    .size(84.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                // 'silueta' simple via emoji; si prefieres, reemplaza por Image(...)
                Text(text = "👟", fontSize = 30.sp)
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = (-6).dp, y = (-6).dp)
                        .size(28.dp)
                        .background(MaterialTheme.colorScheme.primary, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = "camera", tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(16.dp))
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text("¿Tienes un modelo único? Súbelo y compártelo.", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(6.dp))
                Text("Ayuda a la comunidad mostrando tus zapatillas.", style = MaterialTheme.typography.bodySmall)
            }

            Button(onClick = onUploadClick) {
                Text("Cargar zapatilla")
            }
        }
    }
}
