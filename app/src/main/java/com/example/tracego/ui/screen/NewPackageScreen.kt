package com.example.tracego.ui.screen

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.tracego.ui.component.CustomButton
import com.example.tracego.ui.component.CustomHeader
import com.example.tracego.ui.component.CustomInput
import com.example.tracego.ui.component.FooterMenu
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import org.json.JSONObject
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tracego.data.model.Package
import com.example.tracego.ui.viewmodel.PackageListViewModel

@Composable
fun NewPackageListScreen(
    onClickPackageListButton: () -> Unit,
    onClickMapButton: () -> Unit,
    packageListViewModel: PackageListViewModel = viewModel()
) {
    var nombrePaquete by remember { mutableStateOf("") }
    var codigoPaquete by remember { mutableStateOf("") }
    var codigoProveedor by remember { mutableStateOf("") }
    var scannedCode by remember { mutableStateOf<String?>(null) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var jsonProcessed by remember { mutableStateOf(false) }
    var registroExitoso by remember { mutableStateOf(false) }
    var errorRegistro by remember { mutableStateOf<String?>(null) }
    
    val context = LocalContext.current
    val barcodeScanner = remember { 
        BarcodeScanning.getClient(
            com.google.mlkit.vision.barcode.BarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    com.google.mlkit.vision.barcode.common.Barcode.FORMAT_QR_CODE,
                    com.google.mlkit.vision.barcode.common.Barcode.FORMAT_AZTEC,
                    com.google.mlkit.vision.barcode.common.Barcode.FORMAT_DATA_MATRIX
                )
                .build()
        )
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            selectedImageUri = selectedUri
            Log.d("QRScanner", "Imagen seleccionada: $selectedUri")
            
            try {
                val image = InputImage.fromFilePath(context, selectedUri)
                Log.d("QRScanner", "Imagen cargada correctamente")
                Log.d("QRScanner", "Dimensiones de la imagen: ${image.width} x ${image.height}")
                Log.d("QRScanner", "Rotación de la imagen: ${image.rotationDegrees}")
                Log.d("QRScanner", "Formato de la imagen: ${image.format}")
                
                fun scanImage(image: InputImage, scanner: com.google.mlkit.vision.barcode.BarcodeScanner, attempt: String) {
                    Log.d("QRScanner", "Iniciando $attempt...")
                    
                    scanner.process(image)
                        .addOnSuccessListener { barcodes ->
                            if (barcodes.isEmpty()) {
                                Log.w("QRScanner", "No se encontraron códigos QR en la imagen")
                            } else {
                                for ((index, barcode) in barcodes.withIndex()) {
                                    barcode.rawValue?.let { code ->
                                        scannedCode = code

                                        try {
                                            val jsonObject = JSONObject(code)

                                            jsonObject.optString("nombrePaquete")?.let { nombre ->
                                                if (nombre.isNotEmpty()) {
                                                    nombrePaquete = nombre
                                                }
                                            }
                                            
                                            jsonObject.optString("codigoPaquete")?.let { codigo ->
                                                if (codigo.isNotEmpty()) {
                                                    codigoPaquete = codigo
                                                }
                                            }
                                            
                                            jsonObject.optString("codigoProveedor")?.let { proveedor ->
                                                if (proveedor.isNotEmpty()) {
                                                    codigoProveedor = proveedor
                                                }
                                            }
                                            
                                            jsonProcessed = true

                                        } catch (e: Exception) {
                                            Log.w("QRScanner", "El código QR no contiene JSON válido: ${e.message}")
                                            codigoPaquete = code
                                            jsonProcessed = false
                                        }
                                    }
                                }
                            }
                        }
                        .addOnFailureListener { e ->
                            Log.e("QRScanner", "Error en $attempt: ${e.localizedMessage}")
                            Log.e("QRScanner", "Stack trace: ", e)
                        }
                }
                
                scanImage(image, barcodeScanner, "Primer intento")
                
            } catch (e: Exception) {
                Log.e("QRScanner", "Error cargando imagen: ${e.localizedMessage}")
            }
        } ?: run {
            Log.w("QRScanner", "No se seleccionó ninguna imagen")
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CustomHeader()

        Box(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 35.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "RASTREAR NUEVO\nPAQUETE",
                color = Color(0xFF727070),
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            CustomInput(
                input = nombrePaquete,
                onChange = { nombrePaquete = it },
                label = "Nombre del paquete",
                keyboardType = KeyboardType.Text,
            )
            CustomInput(
                input = codigoPaquete,
                onChange = { codigoPaquete = it },
                label = "Código del paquete",
                keyboardType = KeyboardType.Text,
            )
            CustomInput(
                input = codigoProveedor,
                onChange = { codigoProveedor = it },
                label = "Código del proveedor",
                keyboardType = KeyboardType.Text,
            )

            Spacer(modifier = Modifier.height(24.dp))

            CustomButton(
                onClick = {
                    Log.d("QRScanner", "Botón 'Seleccionar imagen QR' presionado")
                    imagePickerLauncher.launch("image/*")
                },
                text = "Seleccionar imagen QR",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            scannedCode?.let { code ->
                if (!jsonProcessed) {
                    Text(
                        text = "Código QR detectado: $code",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }

            CustomButton(
                onClick = {
                    if (nombrePaquete.isNotBlank() && codigoPaquete.isNotBlank() && codigoProveedor.isNotBlank()) {
                        val nuevoPaquete = Package(
                            id = 0,
                            packageName = nombrePaquete,
                            packageCode = codigoPaquete,
                            supplierCode = codigoProveedor,
                            creationDate = "",
                            estimatedDeliveryDate = "",
                            stage = "Registrado",
                            status = "Pendiente"
                        )
                        packageListViewModel.createPackage(
                            nuevoPaquete,
                            onSuccess = {
                                registroExitoso = true
                                errorRegistro = null
                                nombrePaquete = ""
                                codigoPaquete = ""
                                codigoProveedor = ""
                                scannedCode = null
                                jsonProcessed = false
                            },
                            onError = { errorMsg ->
                                registroExitoso = false
                                errorRegistro = errorMsg
                            }
                        )
                    } else {
                        registroExitoso = false
                        errorRegistro = "Completa todos los campos para registrar el paquete."
                    }
                },
                text = "Registrar",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            if (registroExitoso) {
                Text(
                    text = "¡Paquete registrado exitosamente!",
                    color = Color.Green,
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
            errorRegistro?.let { error ->
                Text(
                    text = error,
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        FooterMenu(
            selectedScreen = 2,
            onClickPackageListButton = onClickPackageListButton,
            onClickMapButton = onClickMapButton,
            onClickNewPackageButton = { }
        )
    }
}

