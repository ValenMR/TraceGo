package com.example.tracego.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tracego.ui.component.CustomHeader
import com.example.tracego.ui.component.FooterMenu
import com.example.tracego.ui.component.InfoPackagesCard

@Composable
fun PackageListScreen(
    onClickMapButton: () -> Unit,
    onClickNewPackageButton: () -> Unit,
) {
    val itemsList = List(10) { "Package name $it" }

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
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(itemsList) { index, item ->
                InfoPackagesCard(
                    packageName = item,
                    estimatedDate = "17/05/2000",
                    packageState = "In your city"
                )
            }
        }
    }
}