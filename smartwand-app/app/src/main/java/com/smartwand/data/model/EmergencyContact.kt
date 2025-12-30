package com.smartwand.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Emergency contact entity.
 * 
 * Stores contacts who should be notified in case of emergency
 * (falls, panic button, etc.)
 */
@Entity(tableName = "emergency_contacts")
data class EmergencyContact(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    /** Contact's full name */
    val name: String,
    
    /** Phone number in international format */
    val phone: String,
    
    /** Relationship (e.g., "Padre", "Hermana", "Amigo") */
    val relationship: String,
    
    /** Priority order for notifications (1 = first to notify) */
    val priority: Int = 1,
    
    /** Whether to notify this contact on fall detection */
    val notifyOnFall: Boolean = true,
    
    /** Whether to include GPS location in alerts */
    val includeLocation: Boolean = true,
    
    /** Timestamp of when contact was added */
    val createdAt: Long = System.currentTimeMillis()
)
