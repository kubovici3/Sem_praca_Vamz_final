package com.example.sem_praca.ui.question

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sem_praca.ui.theme.Sem_pracaTheme



@Composable
fun ChoiceQuestionScreen(
    questionText: String,
    options: List<String>,
    onAnswer: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selected by rememberSaveable { mutableStateOf<String?>(null) }

    val backgroundBrush = Brush.horizontalGradient(
        colors = listOf(Color(0xFF0A1E3F), Color(0xFFE0E0E0))
    )

    Box(
        modifier
            .fillMaxSize()
            .background(backgroundBrush)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F1B2D)),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp)
                .fillMaxWidth()
                .widthIn(max = 360.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = questionText,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp,
                    color = Color.White
                )

                options.forEach { option ->
                    val isChosen = option == selected
                    Button(
                        onClick = { selected = option },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isChosen) Color(0xFF3B82F6) else Color(0xFF1F2A3C),
                            contentColor = Color.White
                        )
                    ) {
                        Text(option)
                    }
                }

                Spacer(Modifier.height(8.dp))

                Button(
                    onClick = { selected?.let(onAnswer) },
                    enabled = selected != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3B82F6),
                        contentColor = Color.White
                    )
                ) {
                    Text("Submit")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ChoiceQuestionScreenPreview() {
    Sem_pracaTheme {
        ChoiceQuestionScreen(
            questionText = "What is the capital of France?",
            options = listOf("Paris", "London", "Rome", "Berlin"),
            onAnswer = {}
        )
    }
}
