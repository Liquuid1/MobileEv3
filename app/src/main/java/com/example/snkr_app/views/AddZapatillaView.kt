package com.example.snkr_app.views

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.snkr_app.viewmodels.AddZapatillaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddZapatillaView(onZapatillaSaved: () -> Unit) {
    val vm: AddZapatillaViewModel = viewModel()
    val uiState by vm.uiState.collectAsState()
    val context = LocalContext.current

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val hasCamera = remember {
        context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            vm.onFotoChange(bitmap)
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                cameraLauncher.launch()
            } else {
                scope.launch {
                    snackbarHostState.showSnackbar("El permiso de cámara es necesario para tomar fotos.")
                }
            }
        }
    )

    if (uiState.isSaved) {
        LaunchedEffect(Unit) {
            onZapatillaSaved()
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("Sube tu Zapatilla", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                if (uiState.fotoBitmap != null) {
                    Image(
                        bitmap = uiState.fotoBitmap!!.asImageBitmap(),
                        contentDescription = "Vista previa de la zapatilla",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text("Sin imagen")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (hasCamera) {
                        val permissionStatus = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                            cameraLauncher.launch()
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }
                },
                enabled = hasCamera,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Tomar Foto")
            }
            if (!hasCamera) {
                Text(
                    "No se ha encontrado una cámara en este dispositivo.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = uiState.nombre, onValueChange = vm::onNombreChange, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = uiState.marca, onValueChange = vm::onMarcaChange, label = { Text("Marca") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = uiState.talla, onValueChange = vm::onTallaChange, label = { Text("Talla") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = uiState.color, onValueChange = vm::onColorChange, label = { Text("Color") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = uiState.precio, onValueChange = vm::onPrecioChange, label = { Text("Precio") }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { vm.saveZapatilla() }, modifier = Modifier.fillMaxWidth()) {
                Text("Guardar Zapatilla")
            }
        }
    }
}
