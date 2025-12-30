package com.smartwand.data.repository

import com.smartwand.data.local.dao.IoTDeviceDao
import com.smartwand.data.model.IoTDevice
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for IoT Devices.
 * 
 * Provides a clean API for managing smart home devices.
 */
@Singleton
class DeviceRepository @Inject constructor(
    private val iotDeviceDao: IoTDeviceDao
) {
    
    /** Get all IoT devices */
    fun getAllDevices(): Flow<List<IoTDevice>> {
        return iotDeviceDao.getAllDevices()
    }
    
    /** Get only devices that are currently reachable */
    fun getReachableDevices(): Flow<List<IoTDevice>> {
        return iotDeviceDao.getReachableDevices()
    }
    
    /** Get devices filtered by type (e.g., "light", "switch") */
    fun getDevicesByType(type: String): Flow<List<IoTDevice>> {
        return iotDeviceDao.getDevicesByType(type)
    }
    
    /** Get a single device by ID */
    suspend fun getDeviceById(id: Int): IoTDevice? {
        return iotDeviceDao.getDeviceById(id)
    }
    
    /** Add a new device */
    suspend fun addDevice(device: IoTDevice): Long {
        return iotDeviceDao.insertDevice(device)
    }
    
    /** Update device information */
    suspend fun updateDevice(device: IoTDevice) {
        iotDeviceDao.updateDevice(device)
    }
    
    /** Toggle device on/off state */
    suspend fun toggleDevice(id: Int) {
        val device = iotDeviceDao.getDeviceById(id)
        device?.let {
            iotDeviceDao.updateDeviceState(id, !it.isOn)
        }
    }
    
    /** Set device to specific state */
    suspend fun setDeviceState(id: Int, isOn: Boolean) {
        iotDeviceDao.updateDeviceState(id, isOn)
    }
    
    /** Delete a device */
    suspend fun deleteDevice(device: IoTDevice) {
        iotDeviceDao.deleteDevice(device)
    }
    
    /** Delete a device by ID */
    suspend fun deleteDeviceById(id: Int) {
        iotDeviceDao.deleteDeviceById(id)
    }
}
