package com.bdw.smartwand.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(route: List<LatLng>, modifier: Modifier = Modifier) {
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState
    ) {
        if (route.isNotEmpty()) {
            Polyline(points = route)
        }
    }
}
