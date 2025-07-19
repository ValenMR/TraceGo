package com.example.tracego.ui.screen

import android.preference.PreferenceManager
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tracego.data.model.Package
import com.example.tracego.ui.component.CustomHeader
import com.example.tracego.ui.component.FooterMenu
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tracego.data.api.RetrofitConfig
import com.example.tracego.data.repository.LocationRepository
import com.example.tracego.ui.viewmodel.LocationViewModel

@Composable
fun MapScreen(
    paquete: Package,
    onClickPackageListButton: () -> Unit,
    onClickNewPackageButton: () -> Unit
) {
    val locationViewModel: LocationViewModel = viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                val repo = LocationRepository(RetrofitConfig.locationApi)
                @Suppress("UNCHECKED_CAST")
                return LocationViewModel(repo) as T
            }
        }
    )
    val locationState by locationViewModel.location.collectAsState()

    LaunchedEffect(paquete.packageCode) {
        locationViewModel.fetchLocation(paquete.packageCode)
    }

    Scaffold(
        topBar = {
            CustomHeader()
        },
        bottomBar = {
            FooterMenu(
                selectedScreen = 1,
                onClickPackageListButton = onClickPackageListButton,
                onClickMapButton = {},
                onClickNewPackageButton = onClickNewPackageButton
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(15.dp)
                        )
                        .fillMaxWidth(1f)
                        .size(300.dp)
                ) {
                    if (locationState != null) {
                        MyLocationOsmdroidMap(
                            latitude = locationState!!.latitude.toDouble(),
                            longitude = locationState!!.longitude.toDouble()
                        )
                    } else {
                        Text("Cargando ubicación...", color = Color.White)
                    }
                }

                InfoPackages(
                    packageName = paquete.packageName,
                    estimatedDate = paquete.estimatedDeliveryDate,
                    creationDate = paquete.creationDate,
                    stage = paquete.stage,
                    status = paquete.status
                )
            }
        }
    }
}

@Composable
fun InfoPackages(
    packageName: String,
    estimatedDate: String,
    creationDate: String,
    stage: String,
    status: String
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
                    value = creationDate
                )
                TextInfo(
                    key = "Estimated delivery date: ",
                    value = estimatedDate
                )
                TextInfo(
                    key = "Stage: ",
                    value = stage
                )
                TextInfo(
                    key = "Status: ",
                    value = status
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


@Composable
fun MyLocationOsmdroidMap(latitude: Double, longitude: Double) {
    val context = LocalContext.current

    DisposableEffect(Unit) {
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))
        onDispose { }
    }

    val position = GeoPoint(latitude, longitude)

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            MapView(ctx).apply {
                setMultiTouchControls(false)
                setTileSource(TileSourceFactory.MAPNIK)

                controller.setCenter(position)
                controller.setZoom(20.0)

                val marker = Marker(this)
                marker.position = position
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                marker.title = "Tu paquete"
                overlays.add(marker)

                invalidate()
            }
        },
        update = { mapView ->
            mapView.controller.setCenter(position)
        }
    )
}