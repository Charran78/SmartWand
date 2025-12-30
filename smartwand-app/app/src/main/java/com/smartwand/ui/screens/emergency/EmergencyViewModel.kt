package com.smartwand.ui.screens.emergency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartwand.data.model.EmergencyContact
import com.smartwand.data.repository.EmergencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for Emergency Screen.
 * 
 * Manages emergency contacts and fall history.
 */
@HiltViewModel
class EmergencyViewModel @Inject constructor(
    private val emergencyRepository: EmergencyRepository
) : ViewModel() {
    
    /** All emergency contacts */
    val contacts: StateFlow<List<EmergencyContact>> = emergencyRepository
        .getAllContacts()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    /** UI state for dialogs and loading */
    private val _uiState = MutableStateFlow(EmergencyUiState())
    val uiState: StateFlow<EmergencyUiState> = _uiState.asStateFlow()
    
    /** Add a new emergency contact */
    fun addContact(
        name: String,
        phone: String,
        relationship: String,
        notifyOnFall: Boolean = true
    ) {
        viewModelScope.launch {
            val contactCount = emergencyRepository.getContactCount()
            val contact = EmergencyContact(
                name = name,
                phone = phone,
                relationship = relationship,
                priority = contactCount + 1,
                notifyOnFall = notifyOnFall
            )
            emergencyRepository.addContact(contact)
        }
    }
    
    /** Update an existing contact */
    fun updateContact(contact: EmergencyContact) {
        viewModelScope.launch {
            emergencyRepository.updateContact(contact)
        }
    }
    
    /** Delete a contact */
    fun deleteContact(contact: EmergencyContact) {
        viewModelScope.launch {
            emergencyRepository.deleteContact(contact)
        }
    }
    
    /** Show add contact dialog */
    fun showAddContactDialog() {
        _uiState.value = _uiState.value.copy(showAddDialog = true)
    }
    
    /** Hide add contact dialog */
    fun hideAddContactDialog() {
        _uiState.value = _uiState.value.copy(showAddDialog = false)
    }
    
    /** Trigger emergency call - placeholder for actual implementation */
    fun triggerEmergencyCall() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isEmergencyActive = true)
            // TODO: Implement actual emergency call logic
        }
    }
    
    /** Cancel emergency call */
    fun cancelEmergencyCall() {
        _uiState.value = _uiState.value.copy(isEmergencyActive = false)
    }
}

data class EmergencyUiState(
    val showAddDialog: Boolean = false,
    val isEmergencyActive: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
