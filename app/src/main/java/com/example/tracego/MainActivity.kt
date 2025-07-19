package com.example.tracego

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tracego.ui.screen.PackageListScreen
import com.example.tracego.ui.screen.NewPackageListScreen
import com.example.tracego.ui.screen.MapScreen
import com.example.tracego.ui.screen.AuthScreen
import com.example.tracego.ui.theme.TraceGoTheme
import com.google.gson.Gson
import java.net.URLDecoder
import java.net.URLEncoder
import com.example.tracego.data.model.Package

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TraceGoTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavHost()
                }
            }
        }
    }
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "auth") {
        composable("auth") {
            AuthScreen(
                onClickContinue = {
                    navController.navigate("packageList") {
                        popUpTo("auth") { inclusive = true }
                    }
                }
            )
        }
        composable("packageList") {
            PackageListScreen(
                onClickMapButton = {  },
                onClickNewPackageButton = { navController.navigate("newPackage") },
                onPackageMapClick = { paquete ->
                    val packageJson = URLEncoder.encode(Gson().toJson(paquete), "UTF-8")
                    navController.navigate("mapScreen/$packageJson")
                }
            )
        }
        composable("newPackage") {
            NewPackageListScreen(
                onClickPackageListButton = { navController.popBackStack("packageList", false) },
                onClickMapButton = { navController.navigate("packageList") }
            )
        }
        composable(
            "mapScreen/{packageJson}",
            arguments = listOf(navArgument("packageJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val packageJson = backStackEntry.arguments?.getString("packageJson")
            val paquete = Gson().fromJson(URLDecoder.decode(packageJson, "UTF-8"), Package::class.java)
            MapScreen(
                paquete = paquete,
                onClickPackageListButton = { navController.popBackStack("packageList", false) },
                onClickNewPackageButton = { navController.navigate("newPackage") }
            )
        }
    }
}