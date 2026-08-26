package com.guzelduatr.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Zikir(val isim: String, val adet: Int)

@Composable
fun ZikirlerScreen() {
    val zikirler = listOf(
        Zikir("Sübhanallah", 33),
        Zikir("Elhamdülillah", 33),
        Zikir("Allahu Ekber", 33),
        Zikir("Lâ ilâhe illallah", 100),
        Zikir("Estağfirullah", 100)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = zikirler, key = { it.isim }) { zikir ->
            ZikirCard(zikir)
        }
    }
}

@Composable
fun ZikirCard(zikir: Zikir) {
    var sayac by rememberSaveable { mutableStateOf(0) }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(zikir.isim, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text("Hedef: ${zikir.adet}", style = MaterialTheme.typography.bodyMedium)

            Spacer(Modifier.height(16.dp))

            Text(
                text = "$sayac",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = { if (sayac > 0) sayac-- }) {
                    Text("-")
                }
                Spacer(modifier = Modifier.width(12.dp))
                Button(onClick = { sayac++ }) {
                    Text("+")
                }
                Spacer(modifier = Modifier.width(12.dp))
                OutlinedButton(onClick = { sayac = 0 }) {
                    Text("Sıfırla")
                }
            }
        }
    }
}
