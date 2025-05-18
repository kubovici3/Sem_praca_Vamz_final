package com.example.sem_praca.ui
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.jsonArray


sealed class Slide {
    object Entry : Slide()
    object Waiting : Slide()
    data class Title(val text: String) : Slide()
    data class ChoiceQuestion(val text: String, val options: List<String>) : Slide()
    data class TextQuestion(val text: String) : Slide()
    data class Results(val results: JsonArray) : Slide()
}

/**
 * ViewModel riadi pripojenie k serveru a zobrazovanie "Slidov"
 */
class QuizViewModel : ViewModel() {
    private val _currentSlide = MutableStateFlow<Slide>(Slide.Entry)
    val currentSlide: kotlinx.coroutines.flow.StateFlow<Slide> = _currentSlide.asStateFlow()

    /**  --> Táto časť bola vytvorená s pomocou AI a dokumentácie
     * https://ktor.io/docs/server-create-websocket-application.html#add-starter-code */
    private val client = HttpClient(CIO) {
        install(WebSockets)
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }
    private var session: DefaultClientWebSocketSession? = null

    /** Pripojenie --> Táto časť bola vytvorená s pomocou AI  */
    fun connect(name: String, role: String = "player", serverUrl: String) {
        // don’t reconnect if we already have a session
        if (session != null) return

        viewModelScope.launch {
            client.webSocket(request = {
                url(serverUrl)
            }) {
                session = this

                val join = JoinMessage(type = "join", name = name, role = role)
                send(Frame.Text(Json.encodeToString(JoinMessage.serializer(), join)))

                // loop incoming frames
                for (frame in incoming) {
                    if (frame is Frame.Text) handleIncoming(frame.readText())
                }
            }
        }
    }

    /** Pri tvorení tejto časti som použil AI a dokumentáciu  */
    private fun handleIncoming(text: String) {
        try {
            val msg = Json.parseToJsonElement(text).jsonObject

            if (msg["type"]?.jsonPrimitive?.content != "slide") return
            val content = msg["content"]!!.jsonObject
            when (val slideType = content["type"]?.jsonPrimitive?.content) {
                "waiting" -> {
                    _currentSlide.value = Slide.Waiting
                }
                "title" -> {
                    val txt = content["content"]!!.jsonPrimitive.content
                    _currentSlide.value = Slide.Title(txt)
                }
                "question" -> {
                    when (content["question_type"]?.jsonPrimitive?.content) {
                        "multiple_choice" -> {
                            val question = content["question"]!!.jsonPrimitive.content
                            val options = content["options"]!!.jsonArray.map { it.jsonPrimitive.content }
                            _currentSlide.value = Slide.ChoiceQuestion(question, options)
                        }
                        "text" -> {
                            val question = content["question"]!!.jsonPrimitive.content
                            _currentSlide.value = Slide.TextQuestion(question)
                        }
                        else -> {

                            content["options"]?.jsonArray?.let { arr ->
                                val question = content["question"]!!.jsonPrimitive.content
                                val options = arr.map { it.jsonPrimitive.content }
                                _currentSlide.value = Slide.ChoiceQuestion(question, options)
                            }
                        }
                    }
                }
                "fill_in" -> {
                    val question = content["question"]!!.jsonPrimitive.content
                    _currentSlide.value = Slide.TextQuestion(question)
                }
                "results" -> {
                    val results = content["results"]!!.jsonArray
                    _currentSlide.value = Slide.Results(results)
                }
                else -> {
                    _currentSlide.value = Slide.Waiting
                }
            }
        } catch (e: Exception) {
            Log.e("QuizViewModel", "Error handling incoming slide", e)
            _currentSlide.value = Slide.Waiting
        }
    }

    fun onAnswerSent() {
        _currentSlide.value = Slide.Waiting
    }

    fun sendAnswer(answer: String) {
        viewModelScope.launch {
            session?.send(Frame.Text(
                Json.encodeToString(AnswerMessage.serializer(), AnswerMessage(type="answer", answer=answer))
            ))
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            session?.close(CloseReason(CloseReason.Codes.NORMAL, "App closed"))
        }
        client.close()
    }
}

@Serializable data class JoinMessage(val type: String, val name: String, val role: String)
@Serializable data class AnswerMessage(val type: String, val answer: String)


