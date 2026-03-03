package com.bdw.smartwand.ui.composables

import android.annotation.SuppressLint
import android.bluetooth.le.ScanResult
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@SuppressLint("MissingPermission") // Permissions are handled at the Activity level
@Composable
fun BleDeviceList(
    devices: List<ScanResult>,
    onDeviceClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(devices, key = { it.device.address }) { result ->
            val deviceName = result.device.name ?: "Unnamed Device"
            val deviceAddress = result.device.address
            Text(
                text = "$deviceName - $deviceAddress",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDeviceClick(deviceAddress) }
                    .padding(16.dp)
            )
        }
    }
}
