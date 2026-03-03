package com.bdw.smartwand.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bdw.smartwand.ui.viewmodel.GenieState

@Composable
fun GenieChatScreen(
    genieState: GenieState,
    onSendPrompt: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var prompt by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = prompt,
                onValueChange = { prompt = it },
                label = { Text("Ask Crutch Genie...") },
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = { if (prompt.isNotBlank()) onSendPrompt(prompt) },
                modifier = Modifier.padding(start = 8.dp),
                enabled = genieState !is GenieState.Loading
            ) {
                Text("Send")
            }
        }

        when (genieState) {
            is GenieState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
            }
            is GenieState.Success -> {
                Text(text = genieState.response, modifier = Modifier.padding(top = 16.dp))
            }
            is GenieState.Error -> {
                Text(text = genieState.message, modifier = Modifier.padding(top = 16.dp))
            }
            GenieState.Idle -> {
                // Show nothing or a welcome message
            }
        }
    }
}
