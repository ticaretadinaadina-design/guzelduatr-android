package com.guzelduatr.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.guzelduatr.app.ui.viewmodel.DuaViewModel

@Composable
fun DualarScreen(viewModel: DuaViewModel = hiltViewModel()) {
    val items by viewModel.items.collectAsState()

    var query by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf<com.guzelduatr.app.data.local.DuaEntity?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(value = query, onValueChange = {
            query = it
            if (it.isBlank()) viewModel.search("") else viewModel.search(it)
        }, label = { Text("Ara") }, modifier = Modifier.fillMaxWidth())

        Spacer(Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(items.size) { idx ->
                val dua = items[idx]
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selected = dua }) {
                    Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text(dua.baslik, style = MaterialTheme.typography.titleMedium)
                            Text(dua.kategori, style = MaterialTheme.typography.bodySmall)
                        }
                        IconButton(onClick = { viewModel.toggleFavorite(dua.id, dua.favori) }) {
                            Icon(imageVector = androidx.compose.material.icons.Icons.Default.Favorite, contentDescription = "Favori")
                        }
                    }
                }
            }
        }
    }

    if (selected != null) {
        AlertDialog(onDismissRequest = { selected = null }, confirmButton = {
            TextButton(onClick = { selected = null }) { Text("Kapat") }
        }, title = { Text(selected!!.baslik) }, text = { Text(selected!!.icerik) })
    }
}
