//package com.example.sem_praca.ui.question
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.MaterialTheme.colorScheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.runtime.*
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.sem_praca.ui.theme.Sem_pracaTheme
//import com.example.sem_praca.ui.QuizViewModel
//import kotlinx.coroutines.delay


package com.example.sem_praca.ui.question

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sem_praca.ui.theme.Sem_pracaTheme

/**
 * Obrazovka zobrazuje otázku s možosťou textovej odpovede
 */
@Composable
fun TextQuestionScreen(
    questionText: String,
    onSubmit: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var answer by rememberSaveable { mutableStateOf("") }

    val backgroundBrush = Brush.horizontalGradient(
        listOf(Color(0xFF0A1E3F), Color(0xFFE0E0E0))
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundBrush)
    ) {
        if (questionText.isEmpty()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color(0xFF3B82F6)
            )
        } else {
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
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = questionText,
                        fontSize = 20.sp,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                    OutlinedTextField(
                        value = answer,
                        onValueChange = { answer = it },
                        placeholder = { Text("Your answer", color = Color.LightGray) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (answer.isNotBlank()) {
                                    onSubmit(answer.trim())
                                }
                            }
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorScheme.surface,
                            unfocusedContainerColor = colorScheme.surface,
                            disabledContainerColor = colorScheme.surface,
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                    Button(
                        onClick = { onSubmit(answer.trim()) },
                        enabled = answer.isNotBlank(),
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
}

@Preview(showBackground = true)
@Composable
fun TextQuestionScreenPreview() {
    Sem_pracaTheme {
        TextQuestionScreen(
            questionText = "Solve: 2 + 3 = ?",
            onSubmit = {}
        )
    }
}


