package com.example.tracego.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomInput(
    input: String,
    onChange: (String) -> Unit,
    label: String,
    keyboardCapitalization: KeyboardCapitalization? = null,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label.uppercase(),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp,
            fontWeight = FontWeight.W300,
            modifier = modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )

        BasicTextField(
            value = input,
            onValueChange = onChange,
            modifier = modifier
                .background(
                    MaterialTheme.colorScheme.secondary,
                    RoundedCornerShape(24.dp))
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = Color(0xFF343434)
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = keyboardCapitalization ?: KeyboardCapitalization.None
            )
        )
    }
}