package com.smartwand

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.smartwand.ui.navigation.SmartWandNavHost
import com.smartwand.ui.theme.SmartWandTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Activity - Single Activity Architecture.
 * 
 * This is the only activity in the app. All screens are Compose destinations
 * managed by Navigation Compose.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            SmartWandTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SmartWandNavHost()
                }
            }
        }
    }
}
