package com.example.tracego.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//vista de la autenticacion
@Composable
fun AuthScreen(){
    Box(
        modifier = Modifier
            .height(250.dp)
            .fillMaxWidth(1f)
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ){
        Column (horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "¡Hola!",
                color = Color.White,
                fontSize = 32.sp,
                modifier = Modifier.padding(5.dp),
                fontWeight = FontWeight.Bold

            )
            Text(
                text = "¡Bienvenido a TraceGo!",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.padding(5.dp),
                //fontWeight = FontWeight.Bold
            )

        }

    }
}