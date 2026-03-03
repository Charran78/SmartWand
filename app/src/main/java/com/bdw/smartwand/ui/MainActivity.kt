package com.bdw.smartwand.ui

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bdw.smartwand.data.ble.ConnectionState
import com.bdw.smartwand.ui.composables.BleDeviceList
import com.bdw.smartwand.ui.composables.DeviceControlScreen
import com.bdw.smartwand.ui.composables.GenieChatScreen
import com.bdw.smartwand.ui.composables.MapScreen
import com.bdw.smartwand.ui.theme.SmartWandTheme
import com.bdw.smartwand.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var hasPermissions by remember { mutableStateOf(false) }

            val permissionsToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } else {
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }

            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                hasPermissions = permissions.values.all { it }
            }

            LaunchedEffect(Unit) {
                permissionLauncher.launch(permissionsToRequest)
            }

            val scannedDevices by viewModel.scannedDevices.collectAsStateWithLifecycle()
            val connectionState by viewModel.connectionState.collectAsStateWithLifecycle()
            val isScanning by viewModel.isScanning.collectAsStateWithLifecycle()
            val route by viewModel.currentRoute.collectAsStateWithLifecycle()
            val genieState by viewModel.genieState.collectAsStateWithLifecycle()

            if (hasPermissions) {
                LaunchedEffect(Unit) {
                    viewModel.calculateRoute()
                }
            }

            SmartWandTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (hasPermissions) {
                        when (val state = connectionState) {
                            is ConnectionState.Connected -> {
                                DeviceControlScreen(
                                    services = state.services,
                                    onLedToggle = viewModel::toggleWandLed,
                                    onHornActivate = viewModel::activateWandHorn,
                                    onDisconnectClick = viewModel::disconnect,
                                    modifier = Modifier.padding(innerPadding)
                                )
                            }
                            else -> {
                                if (connectionState == ConnectionState.Disconnected) {
                                    LaunchedEffect(Unit) {
                                        viewModel.startBleScan()
                                    }
                                }
                                Column(modifier = Modifier.padding(innerPadding)) {
                                    val statusText = when {
                                        state is ConnectionState.Connecting -> "Connecting..."
                                        isScanning -> "Scanning..."
                                        state is ConnectionState.Disconnected -> "Disconnected"
                                        else -> state::class.java.simpleName
                                    }
                                    Text(
                                        text = "Status: $statusText",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                    MapScreen(route = route, modifier = Modifier.weight(1f))
                                    BleDeviceList(
                                        devices = scannedDevices,
                                        onDeviceClick = viewModel::connect,
                                        modifier = Modifier.weight(1f)
                                    )
                                    GenieChatScreen(
                                        genieState = genieState,
                                        onSendPrompt = viewModel::sendGeniePrompt,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    } else {
                        Text(
                            text = "Requesting permissions... Grant them to use the app.",
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}
