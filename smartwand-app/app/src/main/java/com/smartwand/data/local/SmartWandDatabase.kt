package com.smartwand.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smartwand.data.local.dao.EmergencyContactDao
import com.smartwand.data.local.dao.FallEventDao
import com.smartwand.data.local.dao.GenieMessageDao
import com.smartwand.data.local.dao.IoTDeviceDao
import com.smartwand.data.model.EmergencyContact
import com.smartwand.data.model.FallEvent
import com.smartwand.data.model.GenieMessage
import com.smartwand.data.model.IoTDevice

/**
 * SmartWand Room Database.
 * 
 * Central database for all local data storage.
 */
@Database(
    entities = [
        EmergencyContact::class,
        IoTDevice::class,
        FallEvent::class,
        GenieMessage::class
    ],
    version = 1,
    exportSchema = true
)
abstract class SmartWandDatabase : RoomDatabase() {
    
    abstract fun emergencyContactDao(): EmergencyContactDao
    abstract fun iotDeviceDao(): IoTDeviceDao
    abstract fun fallEventDao(): FallEventDao
    abstract fun genieMessageDao(): GenieMessageDao
    
    companion object {
        const val DATABASE_NAME = "smartwand_database"
    }
}
