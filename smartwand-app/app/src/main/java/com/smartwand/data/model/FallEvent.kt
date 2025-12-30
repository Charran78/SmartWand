package com.smartwand.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Fall event entity.
 * 
 * Records fall detection events for history and analysis.
 * Useful for medical records and improving detection accuracy.
 */
@Entity(tableName = "fall_events")
data class FallEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    /** Timestamp when the fall was detected */
    val timestamp: Long = System.currentTimeMillis(),
    
    /** GPS latitude at time of fall */
    val latitude: Double? = null,
    
    /** GPS longitude at time of fall */
    val longitude: Double? = null,
    
    /** Address description (reverse geocoded) */
    val address: String? = null,
    
    /** Confidence score of fall detection (0.0 - 1.0) */
    val confidence: Float = 0.0f,
    
    /** Whether the user confirmed this was a real fall */
    val confirmedReal: Boolean? = null,
    
    /** Whether emergency contacts were notified */
    val contactsNotified: Boolean = false,
    
    /** Whether emergency services were called */
    val emergencyServicesContacted: Boolean = false,
    
    /** User notes about the event */
    val notes: String = "",
    
    /** Sensor data snapshot (JSON string) for analysis */
    val sensorDataJson: String? = null
)
