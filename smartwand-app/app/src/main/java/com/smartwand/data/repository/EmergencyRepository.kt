package com.smartwand.data.repository

import com.smartwand.data.local.dao.EmergencyContactDao
import com.smartwand.data.model.EmergencyContact
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for Emergency Contacts.
 * 
 * Provides a clean API for accessing emergency contact data.
 */
@Singleton
class EmergencyRepository @Inject constructor(
    private val emergencyContactDao: EmergencyContactDao
) {
    
    /** Get all emergency contacts ordered by priority */
    fun getAllContacts(): Flow<List<EmergencyContact>> {
        return emergencyContactDao.getAllContacts()
    }
    
    /** Get contacts that should receive fall alerts */
    fun getContactsForFallAlert(): Flow<List<EmergencyContact>> {
        return emergencyContactDao.getContactsForFallAlert()
    }
    
    /** Get a single contact by ID */
    suspend fun getContactById(id: Int): EmergencyContact? {
        return emergencyContactDao.getContactById(id)
    }
    
    /** Add a new emergency contact */
    suspend fun addContact(contact: EmergencyContact): Long {
        return emergencyContactDao.insertContact(contact)
    }
    
    /** Update an existing contact */
    suspend fun updateContact(contact: EmergencyContact) {
        emergencyContactDao.updateContact(contact)
    }
    
    /** Delete a contact */
    suspend fun deleteContact(contact: EmergencyContact) {
        emergencyContactDao.deleteContact(contact)
    }
    
    /** Delete a contact by ID */
    suspend fun deleteContactById(id: Int) {
        emergencyContactDao.deleteContactById(id)
    }
    
    /** Get total number of emergency contacts */
    suspend fun getContactCount(): Int {
        return emergencyContactDao.getContactCount()
    }
}
