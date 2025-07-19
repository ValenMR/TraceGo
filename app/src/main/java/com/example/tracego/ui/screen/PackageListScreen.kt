package com.example.tracego.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tracego.data.model.Package
import com.example.tracego.ui.component.CustomHeader
import com.example.tracego.ui.component.FooterMenu
import com.example.tracego.ui.component.InfoPackagesCard
import com.example.tracego.ui.viewmodel.PackageListUiState
import com.example.tracego.ui.viewmodel.PackageListViewModel

@Composable
fun PackageListScreen(
    onClickMapButton: () -> Unit,
    onClickNewPackageButton: () -> Unit,
    onPackageMapClick: (Package) -> Unit,
    packageListViewModel: PackageListViewModel = viewModel()
) {
    val uiState by packageListViewModel.uiState.collectAsState()
    val isLoading by packageListViewModel.isLoading.collectAsState()
    val errorMessage by packageListViewModel.errorMessage.collectAsState()
    val packages by packageListViewModel.packages.collectAsState()

    LaunchedEffect(Unit) {
        packageListViewModel.loadPackages()
    }

    Scaffold(
        topBar = {
            CustomHeader()
        },
        bottomBar = {
            FooterMenu(
                selectedScreen = 0,
                onClickPackageListButton = {},
                onClickMapButton = onClickMapButton,
                onClickNewPackageButton = onClickNewPackageButton
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState) {
                is PackageListUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                
                is PackageListUiState.Success -> {
                    if (packages.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No hay paquetes disponibles",
                                fontSize = 16.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(packages) { packageItem ->
                                InfoPackagesCard(
                                    packageName = packageItem.packageName,
                                    estimatedDate = packageItem.estimatedDeliveryDate,
                                    packageState = "${packageItem.stage} - ${packageItem.status}",
                                    onIconClick = { onPackageMapClick(packageItem) }
                                )
                            }
                        }
                    }
                }
                
                is PackageListUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = errorMessage ?: "Error desconocido",
                            fontSize = 16.sp,
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                
                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}