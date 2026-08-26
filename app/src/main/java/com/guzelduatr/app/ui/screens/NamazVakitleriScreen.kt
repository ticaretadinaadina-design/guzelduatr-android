package com.guzelduatr.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.guzelduatr.app.ui.viewmodel.PrayerViewModel

@Composable
fun NamazVakitleriScreen(viewModel: PrayerViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()

    var city by remember { mutableStateOf("Istanbul") }
    var country by remember { mutableStateOf("Turkey") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Namaz Vakitleri", style = MaterialTheme.typography.headlineSmall)
                Text(state.location.ifEmpty { "$city, $country" }, color = MaterialTheme.colorScheme.onSurfaceVariant)
                if (state.dateReadable.isNotEmpty()) {
                    Text(state.dateReadable, style = MaterialTheme.typography.bodySmall)
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Button(onClick = { viewModel.fetchTimings(city, country) }) {
                        Text("Güncelle")
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        when {
            state.error != null -> {
                Text("Hata: ${state.error}", color = MaterialTheme.colorScheme.error)
            }
            state.timings.isEmpty() -> {
                Text("Vakit bilgisi yok.")
            }
            else -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    val keys = state.timings.keys.toList().sorted()
                    items(keys) { key ->
                        val time = state.timings[key] ?: ""
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(key, style = MaterialTheme.typography.titleMedium)
                                Text(time, style = MaterialTheme.typography.titleMedium, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
