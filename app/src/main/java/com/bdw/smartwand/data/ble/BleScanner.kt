package com.bdw.smartwand.data.ble

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import com.bdw.smartwand.data.ble.SmartWandUuids.CHARACTERISTIC_UUID_HORN_CONTROL
import com.bdw.smartwand.data.ble.SmartWandUuids.CHARACTERISTIC_UUID_LED_CONTROL
import com.bdw.smartwand.data.ble.SmartWandUuids.SERVICE_UUID_SMARTWAND
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

sealed class ConnectionState {
    object Disconnected : ConnectionState()
    object Connecting : ConnectionState()
    data class Connected(val services: List<BluetoothGattService>) : ConnectionState()
    data class Error(val message: String) : ConnectionState()
}

@SuppressLint("MissingPermission")
class BleScanner(private val context: Context) {

    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    private var gatt: BluetoothGatt? = null

    private val _scannedDevices = MutableStateFlow<List<ScanResult>>(emptyList())
    val scannedDevices: StateFlow<List<ScanResult>> = _scannedDevices

    private val _connectionState = MutableStateFlow<ConnectionState>(ConnectionState.Disconnected)
    val connectionState: StateFlow<ConnectionState> = _connectionState

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    gatt?.discoverServices()
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    _connectionState.value = ConnectionState.Disconnected
                    this@BleScanner.gatt?.close()
                }
            } else {
                _connectionState.value = ConnectionState.Error("Connection failed with status: $status")
                this@BleScanner.gatt?.close()
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                _connectionState.value = ConnectionState.Connected(gatt?.services ?: emptyList())
            } else {
                _connectionState.value = ConnectionState.Error("Service discovery failed with status: $status")
            }
        }

        override fun onCharacteristicWrite(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) {
            if (status != BluetoothGatt.GATT_SUCCESS) {
                // You could expose this write failure to the UI if needed
            }
        }
    }

    private val scanCallback = object : ScanCallback() {
        private val devicesMap = mutableMapOf<String, ScanResult>()

        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            result?.let {
                devicesMap[it.device.address] = it
                _scannedDevices.value = devicesMap.values.toList()
            }
        }

        override fun onBatchScanResults(results: List<ScanResult>?) {
            results?.let {
                it.forEach { result -> devicesMap[result.device.address] = result }
                _scannedDevices.value = devicesMap.values.toList()
            }
        }

        override fun onScanFailed(errorCode: Int) {
            _isScanning.value = false
            // Handle scan failure
        }
    }

    fun startScan() {
        _isScanning.value = true
        bluetoothAdapter?.bluetoothLeScanner?.startScan(scanCallback)
    }

    fun stopScan() {
        _isScanning.value = false
        bluetoothAdapter?.bluetoothLeScanner?.stopScan(scanCallback)
    }

    fun connect(deviceAddress: String) {
        stopScan()
        val device = bluetoothAdapter?.getRemoteDevice(deviceAddress)
        if (device != null) {
            _connectionState.value = ConnectionState.Connecting
            gatt = device.connectGatt(context, false, gattCallback)
        } else {
            _connectionState.value = ConnectionState.Error("Device not found: $deviceAddress")
        }
    }

    fun disconnect() {
        gatt?.disconnect()
    }

    fun writeLedCommand(isOn: Boolean) {
        val payload = if (isOn) byteArrayOf(0x01) else byteArrayOf(0x00)
        writeCharacteristic(SERVICE_UUID_SMARTWAND, CHARACTERISTIC_UUID_LED_CONTROL, payload)
    }

    fun writeHornCommand() {
        // A simple momentary command
        writeCharacteristic(SERVICE_UUID_SMARTWAND, CHARACTERISTIC_UUID_HORN_CONTROL, byteArrayOf(0x01))
    }

    @Suppress("DEPRECATION")
    private fun writeCharacteristic(serviceUuid: UUID, characteristicUuid: UUID, payload: ByteArray) {
        val service = gatt?.getService(serviceUuid)
        val characteristic = service?.getCharacteristic(characteristicUuid)
        if (characteristic != null) {
            characteristic.value = payload
            characteristic.writeType = BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT
            gatt?.writeCharacteristic(characteristic)
        } else {
            // Handle error: characteristic not found
        }
    }
}
