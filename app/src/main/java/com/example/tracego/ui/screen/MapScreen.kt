package com.example.tracego.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tracego.ui.component.CustomHeader
import com.example.tracego.ui.component.FooterMenu
import com.example.tracego.ui.component.IconCircle
import com.example.tracego.ui.component.InfoPackagesCard

@Composable
fun MapScreen() {
    Scaffold(
        topBar = {
            CustomHeader()
        },
        bottomBar = {
            FooterMenu(selectedScreen = 1)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(15.dp)
                        )
                        .size(350.dp)
                )

                InfoPackages(
                    packageName = "Package name",
                    estimatedDate = "17/05/2000",
                    packageState = "In your city"
                )
            }

        }
    }
}

@Composable
fun InfoPackages(
    packageName: String,
    estimatedDate: String,
    packageState: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.secondary,
                    RoundedCornerShape(12.dp)
                )
                .height(170.dp)
                .fillMaxWidth(0.9f)
                .padding(vertical = 5.dp, horizontal = 10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = packageName,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                TextInfo(
                    key = "Creation date: ",
                    value = "17/05/2000"
                )
                TextInfo(
                    key = "Estimated delivery date: ",
                    value = "17/05/2025"
                )
                TextInfo(
                    key = "Stage: ",
                    value = "In the process of packaging"
                )
                TextInfo(
                    key = "Status: ",
                    value = "In process"
                )
            }
        }

    }
}

@Composable
fun TextInfo(
    key: String,
    value: String
) {
    Row {
        Text(
            text = key,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.padding(start = 5.dp),
            text = value,
            fontSize = 14.sp,
        )
    }
}