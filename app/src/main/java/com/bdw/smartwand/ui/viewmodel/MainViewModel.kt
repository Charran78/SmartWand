package com.bdw.smartwand.ui.viewmodel

import android.app.Application
import android.bluetooth.le.ScanResult
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bdw.smartwand.BuildConfig
import com.bdw.smartwand.data.ble.BleScanner
import com.bdw.smartwand.data.ble.ConnectionState
import com.bdw.smartwand.data.ia.GenieService
import com.bdw.smartwand.data.maps.RouteService
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

sealed class GenieState {
    object Idle : GenieState()
    object Loading : GenieState()
    data class Success(val response: String) : GenieState()
    data class Error(val message: String) : GenieState()
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    // BLE Scanner
    private val bleScanner = BleScanner(application)
    val scannedDevices: StateFlow<List<ScanResult>> = bleScanner.scannedDevices
    val connectionState: StateFlow<ConnectionState> = bleScanner.connectionState
    val isScanning: StateFlow<Boolean> = bleScanner.isScanning

    // Maps Route Service
    private val routeService = RouteService()
    private val _currentRoute = MutableStateFlow<List<LatLng>>(emptyList())
    val currentRoute: StateFlow<List<LatLng>> = _currentRoute.asStateFlow()

    // Genie Service
    private val genieService: GenieService? = if (BuildConfig.GEMINI_API_KEY.isNotBlank()) {
        GenieService(BuildConfig.GEMINI_API_KEY)
    } else {
        null
    }
    private val _genieState = MutableStateFlow<GenieState>(GenieState.Idle)
    val genieState: StateFlow<GenieState> = _genieState.asStateFlow()

    init {
        if (genieService == null) {
            _genieState.value = GenieState.Error("Gemini API Key is not configured. Please check your secrets.properties file.")
        }
    }

    // BLE Actions
    fun startBleScan() {
        bleScanner.startScan()
    }

    fun stopBleScan() {
        bleScanner.stopScan()
    }

    fun connect(deviceAddress: String) {
        bleScanner.connect(deviceAddress)
    }

    fun disconnect() {
        bleScanner.disconnect()
    }

    // SmartWand Hardware Actions
    fun toggleWandLed(isOn: Boolean) {
        bleScanner.writeLedCommand(isOn)
    }

    fun activateWandHorn() {
        bleScanner.writeHornCommand()
    }

    // Maps Actions
    fun calculateRoute() {
        viewModelScope.launch {
            val origin = LatLng(1.35, 103.87)
            val destination = LatLng(1.38, 103.90)

            routeService.getRoute(origin, destination)
                .collect { route ->
                    _currentRoute.value = route
                }
        }
    }

    // Genie Actions
    fun sendGeniePrompt(prompt: String) {
        if (genieService == null) {
            _genieState.value = GenieState.Error("Gemini API Key not configured.")
            return
        }
        viewModelScope.launch {
            _genieState.value = GenieState.Loading
            genieService.getResponse(prompt)
                .collect { response ->
                    if (response.startsWith("Error:")) {
                        _genieState.value = GenieState.Error(response)
                    } else {
                        _genieState.value = GenieState.Success(response)
                    }
                }
        }
    }
}
