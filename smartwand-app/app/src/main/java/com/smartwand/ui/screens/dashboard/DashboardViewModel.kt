package com.smartwand.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartwand.data.repository.DeviceRepository
import com.smartwand.data.repository.EmergencyRepository
import com.smartwand.data.repository.FallEventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for Dashboard Screen.
 * 
 * Provides overview data and quick actions.
 */
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val emergencyRepository: EmergencyRepository,
    private val deviceRepository: DeviceRepository,
    private val fallEventRepository: FallEventRepository
) : ViewModel() {
    
    /** Dashboard state combining multiple data sources */
    val dashboardState: StateFlow<DashboardState> = combine(
        emergencyRepository.getAllContacts(),
        deviceRepository.getReachableDevices(),
        fallEventRepository.getRecentEvents(5)
    ) { contacts, devices, fallEvents ->
        DashboardState(
            emergencyContactCount = contacts.size,
            connectedDeviceCount = devices.size,
            recentFallCount = fallEvents.count { it.confirmedReal == true },
            isConnectedToWand = false, // TODO: BLE connection status
            wandBatteryLevel = null // TODO: Get from BLE
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardState()
    )
    
    /** Quick action state */
    private val _quickActionState = MutableStateFlow(QuickActionState())
    val quickActionState: StateFlow<QuickActionState> = _quickActionState.asStateFlow()
    
    /** Toggle flashlight - placeholder */
    fun toggleFlashlight() {
        viewModelScope.launch {
            val current = _quickActionState.value.isFlashlightOn
            _quickActionState.value = _quickActionState.value.copy(
                isFlashlightOn = !current
            )
            // TODO: Send BLE command to muleta
        }
    }
    
    /** Trigger horn - placeholder */
    fun triggerHorn() {
        viewModelScope.launch {
            _quickActionState.value = _quickActionState.value.copy(
                isHornActive = true
            )
            // TODO: Send BLE command and auto-reset after delay
        }
    }
    
    /** Media control - previous track */
    fun previousTrack() {
        // TODO: Send media control intent
    }
    
    /** Media control - next track */
    fun nextTrack() {
        // TODO: Send media control intent
    }
    
    /** Trigger emergency - calls emergency contacts */
    fun triggerEmergency() {
        viewModelScope.launch {
            // TODO: Implement emergency flow
            // 1. Get location
            // 2. Record fall event
            // 3. Notify contacts
            // 4. Optionally call emergency services
        }
    }
}

data class DashboardState(
    val emergencyContactCount: Int = 0,
    val connectedDeviceCount: Int = 0,
    val recentFallCount: Int = 0,
    val isConnectedToWand: Boolean = false,
    val wandBatteryLevel: Int? = null
)

data class QuickActionState(
    val isFlashlightOn: Boolean = false,
    val isHornActive: Boolean = false
)
