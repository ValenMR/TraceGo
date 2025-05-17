package com.example.tracego

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tracego.ui.screen.AuthScreen
import com.example.tracego.ui.screen.MapScreen
import com.example.tracego.ui.screen.NewPackageListScreen
import com.example.tracego.ui.screen.PackageListScreen
import com.example.tracego.ui.theme.TraceGoTheme

enum class TraceGoScreen() {
    Auth,
    Map,
    NewPackage,
    PackageList
}

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TraceGoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    TraceGoApp()
                }
            }
        }
    }
}

@Composable
fun TraceGoApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = TraceGoScreen.Auth.name,
        modifier = Modifier
            .fillMaxSize()
    ) {
        composable(route = TraceGoScreen.Auth.name) {
            AuthScreen(
                onClickContinue = { navController.navigate(TraceGoScreen.PackageList.name) }
            )
        }
        composable(route = TraceGoScreen.PackageList.name) {
            PackageListScreen(
                onClickMapButton = { navController.navigate(TraceGoScreen.Map.name) },
                onClickNewPackageButton = { navController.navigate(TraceGoScreen.NewPackage.name) }
            )
        }
        composable(route = TraceGoScreen.Map.name) {
            MapScreen(
                onClickPackageListButton = { navController.navigate(TraceGoScreen.PackageList.name) },
                onClickNewPackageButton = { navController.navigate(TraceGoScreen.NewPackage.name) },
            )
        }
        composable(route = TraceGoScreen.NewPackage.name) {
            NewPackageListScreen(
                onClickPackageListButton = { navController.navigate(TraceGoScreen.PackageList.name) },
                onClickMapButton = { navController.navigate(TraceGoScreen.Map.name) },
            )
        }
    }
}