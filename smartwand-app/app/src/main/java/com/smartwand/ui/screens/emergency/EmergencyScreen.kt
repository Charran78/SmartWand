package com.smartwand.ui.screens.emergency

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartwand.R
import com.smartwand.ui.theme.EmergencyRed
import com.smartwand.ui.theme.SmartWandTheme

/**
 * Emergency Screen.
 * 
 * Shows:
 * - Emergency contacts list
 * - Panic button
 * - Fall history
 * - Test alert button
 */
@Composable
fun EmergencyScreen() {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Add contact dialog */ },
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.emergency_add_contact)
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.emergency_title),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Emergency Call Button
            EmergencyCallButton(
                onClick = { /* TODO: Make emergency call */ }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Contacts Section
            Text(
                text = stringResource(R.string.emergency_contacts),
                style = MaterialTheme.typography.titleMedium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Contacts List (placeholder)
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Placeholder contacts - will be replaced with real data
                items(0) { 
                    ContactCard(
                        name = "Placeholder",
                        phone = "+34 000 000 000",
                        relationship = "Familiar"
                    )
                }
                
                // Empty state
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "No hay contactos de emergencia",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Toca + para añadir uno",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Test Alert Button
            FilledTonalButton(
                onClick = { /* TODO: Test alert */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.emergency_test_alert))
            }
        }
    }
}

@Composable
private fun EmergencyCallButton(onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = EmergencyRed
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.onError
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.emergency_call_now),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onError
            )
        }
    }
}

@Composable
private fun ContactCard(
    name: String,
    phone: String,
    relationship: String
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        ListItem(
            headlineContent = { Text(name) },
            supportingContent = { Text("$relationship • $phone") },
            leadingContent = {
                Icon(Icons.Filled.Person, contentDescription = null)
            },
            trailingContent = {
                Icon(
                    imageVector = Icons.Filled.Phone,
                    contentDescription = "Llamar",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EmergencyScreenPreview() {
    SmartWandTheme {
        EmergencyScreen()
    }
}
