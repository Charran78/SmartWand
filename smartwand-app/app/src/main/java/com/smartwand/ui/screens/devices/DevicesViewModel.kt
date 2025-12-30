package com.smartwand.ui.screens.devices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartwand.data.model.IoTDevice
import com.smartwand.data.repository.DeviceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for Devices Screen.
 * 
 * Manages IoT device listing and control.
 */
@HiltViewModel
class DevicesViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository
) : ViewModel() {
    
    /** All IoT devices */
    val devices: StateFlow<List<IoTDevice>> = deviceRepository
        .getAllDevices()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    /** UI state */
    private val _uiState = MutableStateFlow(DevicesUiState())
    val uiState: StateFlow<DevicesUiState> = _uiState.asStateFlow()
    
    /** Toggle device on/off */
    fun toggleDevice(deviceId: Int) {
        viewModelScope.launch {
            try {
                deviceRepository.toggleDevice(deviceId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Error al controlar dispositivo: ${e.message}"
                )
            }
        }
    }
    
    /** Add a new device */
    fun addDevice(
        name: String,
        type: String,
        location: String,
        connectionId: String = ""
    ) {
        viewModelScope.launch {
            val device = IoTDevice(
                name = name,
                type = type,
                location = location,
                connectionId = connectionId
            )
            deviceRepository.addDevice(device)
            _uiState.value = _uiState.value.copy(showAddDialog = false)
        }
    }
    
    /** Delete a device */
    fun deleteDevice(device: IoTDevice) {
        viewModelScope.launch {
            deviceRepository.deleteDevice(device)
        }
    }
    
    /** Show add device dialog */
    fun showAddDeviceDialog() {
        _uiState.value = _uiState.value.copy(showAddDialog = true)
    }
    
    /** Hide add device dialog */
    fun hideAddDeviceDialog() {
        _uiState.value = _uiState.value.copy(showAddDialog = false)
    }
    
    /** Clear error message */
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class DevicesUiState(
    val showAddDialog: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
