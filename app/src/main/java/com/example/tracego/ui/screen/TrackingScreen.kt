package com.example.tracego.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tracego.ui.component.CustomButton
import com.example.tracego.ui.component.CustomInput
import com.example.tracego.ui.component.FooterMenu

@Composable
fun TrackPackageScreen() {
    var nombrePaquete by remember { mutableStateOf("") }
    var codigoPaquete by remember { mutableStateOf("") }
    var codigoProveedor by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Header con gradiente
        Box(
            modifier = Modifier
                .height(135.dp)
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.tertiary),
                        start = Offset(0f, 0f),
                        end = Offset(2000f, 2000f)
                    )
                )
        ) {
            Text(
                text = "TraceGo",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 16.dp)
            )

        }

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

        // Inputs y botón
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            CustomInput(
                input = nombrePaquete,
                onChange = { nombrePaquete = it },
                label = "Nombre del paquete"
            )
            CustomInput(
                input = codigoPaquete,
                onChange = { codigoPaquete = it },
                label = "Código del paquete"
            )
            CustomInput(
                input = codigoProveedor,
                onChange = { codigoProveedor = it },
                label = "Código del proveedor"
            )

            Spacer(modifier = Modifier.height(24.dp))

            CustomButton(
                onClick = {
                    // Acción del botón
                },
                text = "Registrar",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        // Footer
        FooterMenu(selectedScreen = 2)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TrackPackageScreenPreview() {
    TrackPackageScreen()
}

