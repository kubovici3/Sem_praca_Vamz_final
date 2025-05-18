package com.example.sem_praca

import com.example.sem_praca.ui.QuizViewModel
import com.example.sem_praca.ui.Slide
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sem_praca.ui.entry.QuizEntryScreen
import com.example.sem_praca.ui.question.ChoiceQuestionScreen
import com.example.sem_praca.ui.question.TextQuestionScreen
import com.example.sem_praca.ui.summary.SummaryScreen
import com.example.sem_praca.ui.waiting.WaitingScreen
import com.example.sem_praca.ui.theme.Sem_pracaTheme
import com.example.sem_praca.ui.waiting.TitleScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Sem_pracaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val quizViewModel: QuizViewModel = viewModel()
                    val slide by quizViewModel.currentSlide.collectAsState()

                    when (slide) {
                        is Slide.Entry -> {
                            QuizEntryScreen(
                                onJoin = { name ->
                                    quizViewModel.connect(
                                        name      = name,
                                        role      = "player",
                                        serverUrl = "ws://10.0.2.2:8080"
                                    )
                                }
                            )
                        }
                        is Slide.Title -> {
                            val title = (slide as Slide.Title).text
                            TitleScreen(titleText = title)
                        }
                        is Slide.Waiting -> {
                            WaitingScreen(players = emptyList())
                        }
                        is Slide.ChoiceQuestion -> {
                            val cq = slide as Slide.ChoiceQuestion

                            ChoiceQuestionScreen(

                                questionText = cq.text,
                                options      = cq.options,
                                onAnswer     = { chosenAnswer ->
                                    quizViewModel.sendAnswer(chosenAnswer)
                                    quizViewModel.onAnswerSent()

                                }
                            )
                        }
                        is Slide.TextQuestion -> {
                            val tq = slide as Slide.TextQuestion
                            TextQuestionScreen(
                                questionText = tq.text,
                                onSubmit     = { answer ->
                                    quizViewModel.sendAnswer(answer)
                                    quizViewModel.onAnswerSent()

                                }
                            )
                        }

                        is Slide.Results -> {
                            val res = (slide as Slide.Results).results
                            SummaryScreen(
                                results = res
                            )
                        }
                    }
                    }
                }
            }
        }
    }




