package com.smartwand.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.smartwand.data.model.EmergencyContact
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for Emergency Contacts.
 */
@Dao
interface EmergencyContactDao {
    
    @Query("SELECT * FROM emergency_contacts ORDER BY priority ASC")
    fun getAllContacts(): Flow<List<EmergencyContact>>
    
    @Query("SELECT * FROM emergency_contacts WHERE notifyOnFall = 1 ORDER BY priority ASC")
    fun getContactsForFallAlert(): Flow<List<EmergencyContact>>
    
    @Query("SELECT * FROM emergency_contacts WHERE id = :id")
    suspend fun getContactById(id: Int): EmergencyContact?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: EmergencyContact): Long
    
    @Update
    suspend fun updateContact(contact: EmergencyContact)
    
    @Delete
    suspend fun deleteContact(contact: EmergencyContact)
    
    @Query("DELETE FROM emergency_contacts WHERE id = :id")
    suspend fun deleteContactById(id: Int)
    
    @Query("SELECT COUNT(*) FROM emergency_contacts")
    suspend fun getContactCount(): Int
}
