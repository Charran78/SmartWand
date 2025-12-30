package com.smartwand.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartwand.R
import com.smartwand.ui.theme.SmartWandTheme

/**
 * Settings Screen.
 * 
 * App configuration options:
 * - Language selection
 * - Theme (light/dark/system)
 * - Accessibility options
 * - Notifications
 * - About
 */
@Composable
fun SettingsScreen() {
    var darkMode by remember { mutableStateOf(false) }
    var largeText by remember { mutableStateOf(false) }
    var notifications by remember { mutableStateOf(true) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.settings_title),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // General Section
        SettingsSection(title = stringResource(R.string.settings_general)) {
            SettingsItem(
                icon = { Icon(Icons.Filled.Language, contentDescription = null) },
                title = stringResource(R.string.settings_language),
                subtitle = "Español",
                onClick = { /* TODO: Language picker */ }
            )
            
            SettingsItemWithSwitch(
                icon = { Icon(Icons.Filled.DarkMode, contentDescription = null) },
                title = stringResource(R.string.settings_theme),
                subtitle = if (darkMode) 
                    stringResource(R.string.settings_theme_dark) 
                else 
                    stringResource(R.string.settings_theme_light),
                checked = darkMode,
                onCheckedChange = { darkMode = it }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Accessibility Section
        SettingsSection(title = stringResource(R.string.settings_accessibility)) {
            SettingsItemWithSwitch(
                icon = { Icon(Icons.Filled.Accessibility, contentDescription = null) },
                title = stringResource(R.string.settings_large_text),
                subtitle = null,
                checked = largeText,
                onCheckedChange = { largeText = it }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Notifications Section
        SettingsSection(title = stringResource(R.string.settings_notifications)) {
            SettingsItemWithSwitch(
                icon = { Icon(Icons.Filled.Notifications, contentDescription = null) },
                title = stringResource(R.string.settings_notifications),
                subtitle = null,
                checked = notifications,
                onCheckedChange = { notifications = it }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // About Section
        SettingsSection(title = stringResource(R.string.settings_about)) {
            SettingsItem(
                icon = { Icon(Icons.Filled.Info, contentDescription = null) },
                title = stringResource(R.string.settings_version),
                subtitle = "0.1.0",
                onClick = { }
            )
            
            SettingsItem(
                icon = { Icon(Icons.AutoMirrored.Filled.Help, contentDescription = null) },
                title = "Ayuda y soporte",
                subtitle = null,
                onClick = { /* TODO: Help screen */ }
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            content()
        }
    }
}

@Composable
private fun SettingsItem(
    icon: @Composable () -> Unit,
    title: String,
    subtitle: String?,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = { Text(title) },
        supportingContent = subtitle?.let { { Text(it) } },
        leadingContent = icon,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun SettingsItemWithSwitch(
    icon: @Composable () -> Unit,
    title: String,
    subtitle: String?,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    ListItem(
        headlineContent = { Text(title) },
        supportingContent = subtitle?.let { { Text(it) } },
        leadingContent = icon,
        trailingContent = {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    SmartWandTheme {
        SettingsScreen()
    }
}
