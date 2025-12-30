package com.smartwand.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * IoT Device entity.
 * 
 * Represents a smart device that can be controlled via SmartWand.
 * Initially focused on simple toggle devices (lights, switches).
 */
@Entity(tableName = "iot_devices")
data class IoTDevice(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    /** User-defined name for the device */
    val name: String,
    
    /** Device type (e.g., "light", "switch", "outlet") */
    val type: String,
    
    /** Location in the house (e.g., "Salón", "Cocina") */
    val location: String = "",
    
    /** Current state - true = on, false = off */
    val isOn: Boolean = false,
    
    /** Icon identifier for the device */
    val iconName: String = "lightbulb",
    
    /** Device connection info (IP, MAC, or cloud ID) */
    val connectionId: String = "",
    
    /** Protocol used (e.g., "mqtt", "http", "ble") */
    val protocol: String = "http",
    
    /** Whether the device is currently reachable */
    val isReachable: Boolean = true,
    
    /** Timestamp of last state change */
    val lastUpdated: Long = System.currentTimeMillis()
)
