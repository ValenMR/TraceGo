package com.example.tracego.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tracego.R
import com.example.tracego.ui.component.CustomButton
import com.example.tracego.ui.component.CustomInput
import com.example.tracego.ui.viewmodel.AuthUiState
import com.example.tracego.ui.viewmodel.AuthViewModel

@Composable
fun AuthScreen(
    onClickContinue: () -> Unit,
    authViewModel: AuthViewModel = viewModel()
){

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val uiState by authViewModel.uiState.collectAsState()
    val isLoading by authViewModel.isLoading.collectAsState()
    val errorMessage by authViewModel.errorMessage.collectAsState()

    LaunchedEffect(uiState) {
        when (uiState) {
            is AuthUiState.Success -> {
                onClickContinue()
            }
            is AuthUiState.Error -> {
                // Todo
            }
            else -> {
                // Todo
            }
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        Box(
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth(1f)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.tertiary),
                        start = Offset(0f, 0f),
                        end = Offset(2000f, 2000f)
                    )
                ),
            contentAlignment = Alignment.Center
        ){
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "¡Hola!",
                    color = Color.White,
                    fontSize = 42.sp,
                    modifier = Modifier.padding(5.dp),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "¡Bienvenido a TraceGo!",
                    color = Color.White,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(5.dp),
                )
            }
        }

        Text(
            text = "INGRESA CON TU CUENTA",
            color = Color.Gray,
            fontSize = 25.sp,
            letterSpacing = 5.sp,
            lineHeight = 35.sp,
            modifier = Modifier
                .padding(40.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(30.dp))
                .padding(horizontal = 35.dp, vertical = 3.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            CustomInput(
                input = email,
                onChange = { email = it },
                label = "Usuario",
                keyboardCapitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Email,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomInput(
                input = password,
                onChange = { password = it },
                label = "Contraseña",
                keyboardCapitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Password,
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
            )

            Spacer(modifier = Modifier.height(52.dp))

            CustomButton(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        authViewModel.login(email, password)
                    }
                },
                text = if (isLoading) "Iniciando sesión..." else "Continuar",
                enabled = !isLoading && email.isNotEmpty() && password.isNotEmpty(),
                modifier = Modifier
                    .padding(horizontal = 55.dp, vertical = 3.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row (
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircleIcon(
                    imageRes = R.drawable.logo_original
                )
            }

            Spacer(modifier = Modifier.width(30.dp))

            Text(
                text = "Copyright © 2025 TraceGo\nTodos los derechos reservados",
                fontSize = 10.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 13.dp),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CircleIcon(imageRes: Int){
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color.White, shape = RoundedCornerShape(32.dp)),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
    }
}