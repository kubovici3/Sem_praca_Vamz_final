package com.example.sem_praca.ui.waiting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.sem_praca.ui.theme.Sem_pracaTheme



@Composable
fun WaitingScreen(
    players: List<String> = emptyList(),
    modifier: Modifier = Modifier
) {
    val backgroundBrush = Brush.horizontalGradient(
        colors = listOf(Color(0xFF0A1E3F), Color(0xFFE0E0E0))
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundBrush),
        contentAlignment = Alignment.Center
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
                verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                    .padding(24.dp)
                    .wrapContentSize(),
            ) {
                Text(
                    text = "Čaká sa na ostatných hráčov...",
                    fontSize = 20.sp,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                CircularProgressIndicator(
                    color = Color(0xFF3B82F6),
                    strokeWidth = 4.dp,
                    modifier = Modifier.size(48.dp)
                )

                if (players.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Pripojený:",
                            fontSize = 16.sp,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        players.forEach { player ->
                            Text(
                                text = "• $player",
                                fontSize = 16.sp,
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium
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
fun WaitingScreenPreview() {
    Sem_pracaTheme {
        WaitingScreen(players = listOf("", ""))
    }
}
