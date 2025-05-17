package com.example.tracego

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.tracego.ui.component.InfoPackagesCard
import com.example.tracego.ui.screen.AuthScreen
import com.example.tracego.ui.screen.PackageListScreen
import com.example.tracego.ui.theme.TraceGoTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TraceGoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    PackageListScreen()
                }
            }
        }
    }
}