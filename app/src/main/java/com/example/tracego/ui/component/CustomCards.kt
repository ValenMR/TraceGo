package com.example.tracego.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tracego.data.model.Package
import androidx.compose.foundation.clickable

@Composable
fun InfoPackagesCard(
    packageName: String,
    estimatedDate: String,
    packageState: String,
    onIconClick: (() -> Unit)? = null
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
                .height(80.dp)
                .fillMaxWidth(0.9f)
                .padding(vertical = 5.dp, horizontal = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = packageName,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = estimatedDate,
                        fontSize = 14.sp,
                    )
                    Text(
                        text = packageState,
                        fontSize = 14.sp,
                    )
                }
                IconCircle(Icons.Default.Place, onClick = onIconClick)
            }
        }
    }
}

@Composable
fun IconCircle(
    icon: ImageVector,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .size(70.dp)
            .clip(CircleShape)
            .background(Color.White)
            .let { if (onClick != null) it.clickable { onClick() } else it },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(40.dp)
        )
    }
}

@Preview
@Composable
fun InfoPackagesCardPreview() {
    InfoPackagesCard(
        packageName = "Package name",
        estimatedDate = "17/05/2000",
        packageState = "In your city"
    )
}