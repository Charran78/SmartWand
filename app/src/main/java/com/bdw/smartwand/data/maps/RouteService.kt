package com.bdw.smartwand.data.maps

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RouteService {

    // Simulate a network call to get a route
    fun getRoute(origin: LatLng, destination: LatLng): Flow<List<LatLng>> = flow {
        // Simulate network delay
        delay(1000)

        // Simulate a simple route
        val route = listOf(
            origin,
            LatLng(origin.latitude + 0.01, origin.longitude + 0.01),
            LatLng(origin.latitude, origin.longitude + 0.02),
            destination
        )
        emit(route)
    }
}
