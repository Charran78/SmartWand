package com.bdw.smartwand.ui.composables

import android.bluetooth.BluetoothGattService
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bdw.smartwand.data.ble.SmartWandUuids

@Composable
fun DeviceControlScreen(
    services: List<BluetoothGattService>,
    onLedToggle: (Boolean) -> Unit,
    onHornActivate: () -> Unit,
    onDisconnectClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val smartWandService = services.find { it.uuid == SmartWandUuids.SERVICE_UUID_SMARTWAND }

    Column(modifier = modifier.padding(16.dp)) {
        Text("Device Control")

        Spacer(modifier = Modifier.height(16.dp))

        if (smartWandService != null) {
            var isLedOn by remember { mutableStateOf(false) }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Toggle LED")
                Switch(
                    checked = isLedOn,
                    onCheckedChange = {
                        isLedOn = it
                        onLedToggle(it)
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onHornActivate, modifier = Modifier.fillMaxWidth()) {
                Text("Activate Horn")
            }
        } else {
            Text("SmartWand service not found on this device.")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onDisconnectClick) {
            Text("Disconnect")
        }
    }
}
