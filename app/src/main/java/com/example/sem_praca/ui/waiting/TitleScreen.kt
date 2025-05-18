package com.example.sem_praca.ui.waiting

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
import com.example.sem_praca.ui.theme.Sem_pracaTheme

/**
 * Obrazovka zobrazuje oznámenia pre hráča (napríklad začiatok kola)
 */
@Composable
fun TitleScreen(
    titleText: String,
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
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F1B2D)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 32.dp)
                    .wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = titleText,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 28.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TitleScreenPreview() {
    Sem_pracaTheme {
        TitleScreen(titleText = "Prvé kolo:")
    }
}
