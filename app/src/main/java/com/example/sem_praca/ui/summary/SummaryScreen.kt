package com.example.sem_praca.ui.summary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.int
import com.example.sem_praca.ui.theme.Sem_pracaTheme

/**
 * Obrazovka zobrazujúca výpis aktuálneho počtu bodov a mená hráčov, ktorý tieto body získali
 */
@Composable
fun SummaryScreen(
    results: JsonArray,
    modifier: Modifier = Modifier
) {
    val backgroundBrush = Brush.horizontalGradient(
        listOf(Color(0xFF0A1E3F), Color(0xFFE0E0E0))
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundBrush)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F1B2D)),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp)
                .fillMaxWidth()
                .widthIn(max = 360.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "Round Results",
                    fontSize = 20.sp,
                    color = Color(0xFFFFA000),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    results.forEach { element ->
                        val obj = element.jsonObject
                        val name = obj["name"]?.jsonPrimitive?.content ?: ""
                        val score = obj["score"]?.jsonPrimitive?.int ?: 0
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "•",
                                fontSize = 18.sp,
                                color = Color(0xFFFFA000),
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                text = name,
                                fontSize = 16.sp,
                                color = Color.White,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "${score} points",
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SummaryScreenPreview() {
    Sem_pracaTheme {
        val dummyResults = buildJsonArray {
            add(buildJsonObject { put("name", "Alice"); put("score", 2) })
            add(buildJsonObject { put("name", "Bob"); put("score", 1) })
            add(buildJsonObject { put("name", "Charlie"); put("score", 0) })
        }
        SummaryScreen(results = dummyResults)
    }
}


