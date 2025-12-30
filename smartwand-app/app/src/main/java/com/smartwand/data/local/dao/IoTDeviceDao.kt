package com.smartwand.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.smartwand.data.model.IoTDevice
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for IoT Devices.
 */
@Dao
interface IoTDeviceDao {
    
    @Query("SELECT * FROM iot_devices ORDER BY location, name")
    fun getAllDevices(): Flow<List<IoTDevice>>
    
    @Query("SELECT * FROM iot_devices WHERE isReachable = 1")
    fun getReachableDevices(): Flow<List<IoTDevice>>
    
    @Query("SELECT * FROM iot_devices WHERE type = :type")
    fun getDevicesByType(type: String): Flow<List<IoTDevice>>
    
    @Query("SELECT * FROM iot_devices WHERE id = :id")
    suspend fun getDeviceById(id: Int): IoTDevice?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevice(device: IoTDevice): Long
    
    @Update
    suspend fun updateDevice(device: IoTDevice)
    
    @Query("UPDATE iot_devices SET isOn = :isOn, lastUpdated = :timestamp WHERE id = :id")
    suspend fun updateDeviceState(id: Int, isOn: Boolean, timestamp: Long = System.currentTimeMillis())
    
    @Delete
    suspend fun deleteDevice(device: IoTDevice)
    
    @Query("DELETE FROM iot_devices WHERE id = :id")
    suspend fun deleteDeviceById(id: Int)
}
