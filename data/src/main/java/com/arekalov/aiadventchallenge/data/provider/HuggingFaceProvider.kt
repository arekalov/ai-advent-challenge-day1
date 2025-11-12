package com.arekalov.aiadventchallenge.data.provider

import android.util.Log
import com.arekalov.aiadventchallenge.domain.model.Message
import com.arekalov.aiadventchallenge.domain.model.ModelMetrics
import com.arekalov.aiadventchallenge.domain.provider.LLMProvider
import com.arekalov.aiadventchallenge.domain.provider.LLMResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * HuggingFace Inference API Provider
 */
class HuggingFaceProvider(
    private val modelId: String,
    private val modelName: String,
    private val modelDescription: String,
    private val apiToken: String
) : LLMProvider {

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("HuggingFaceProvider", message)
                }
            }
            level = LogLevel.INFO
        }
        defaultRequest {
            header("Authorization", "Bearer $apiToken")
        }
    }

    override suspend fun sendMessage(
        systemPrompt: String,
        messages: List<Message>,
        temperature: Float
    ): Result<LLMResponse> = runCatching {
        val startTime = System.currentTimeMillis()
        
        // Формируем messages для HuggingFace API
        val hfMessages = buildList {
            // Системный промпт
            add(HFMessage(role = "system", content = systemPrompt))
            
            // История сообщений
            messages.forEach { msg ->
                add(
                    HFMessage(
                        role = if (msg.isUser) "user" else "assistant",
                        content = msg.text
                    )
                )
            }
        }

        val request = HFChatRequest(
            model = modelId,
            messages = hfMessages,
            stream = false
        )

        Log.d("HuggingFaceProvider", "Sending request to model: $modelId")
        Log.d("HuggingFaceProvider", "Request: $request")

        val response = try {
            client.post("https://router.huggingface.co/v1/chat/completions") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        } catch (e: Exception) {
            Log.e("HuggingFaceProvider", "Request failed", e)
            throw e
        }

        val responseTimeMs = System.currentTimeMillis() - startTime
        val bodyText = response.bodyAsText()
        
        Log.d("HuggingFaceProvider", "Raw response: $bodyText")

        // Проверяем, не ошибка ли это
        if (bodyText.contains("\"error\"")) {
            val errorJson = Json.parseToJsonElement(bodyText).jsonObject
            val errorMessage = errorJson["error"]?.jsonPrimitive?.content ?: "Unknown error"
            throw Exception("HuggingFace API error: $errorMessage")
        }

        // Парсим OpenAI-совместимый формат
        val (text, hfResponse) = try {
            val response: HFChatResponse = Json {
                ignoreUnknownKeys = true
                isLenient = true
            }.decodeFromString(bodyText)
            val content = response.choices.firstOrNull()?.message?.content
                ?: throw Exception("No message content in response")
            content to response
        } catch (e: Exception) {
            Log.e("HuggingFaceProvider", "Failed to parse response: $bodyText", e)
            throw Exception("Failed to parse HuggingFace response: ${e.message}")
        }

        // Используем usage из ответа, если доступно, иначе оценка
        val metrics = if (hfResponse.usage != null) {
            ModelMetrics(
                responseTimeMs = responseTimeMs,
                inputTokens = hfResponse.usage.prompt_tokens,
                outputTokens = hfResponse.usage.completion_tokens,
                modelName = modelName,
                estimatedCost = 0.0 // HuggingFace Inference API бесплатный
            )
        } else {
            ModelMetrics(
                responseTimeMs = responseTimeMs,
                inputTokens = estimateTokens(hfMessages),
                outputTokens = estimateTokens(text),
                modelName = modelName,
                estimatedCost = 0.0
            )
        }

        Log.d("HuggingFaceProvider", "Response: $text")
        Log.d("HuggingFaceProvider", "Metrics: $metrics")

        LLMResponse(
            text = text,
            metrics = metrics
        )
    }

    override fun getModelName(): String = modelName

    override fun getModelDescription(): String = modelDescription

    override suspend fun isAvailable(): Boolean {
        return try {
            // Простая проверка доступности с использованием нового API
            val testRequest = HFChatRequest(
                model = modelId,
                messages = listOf(HFMessage(role = "user", content = "test")),
                stream = false
            )
            
            client.post("https://router.huggingface.co/v1/chat/completions") {
                contentType(ContentType.Application.Json)
                setBody(testRequest)
            }
            true
        } catch (e: Exception) {
            Log.e("HuggingFaceProvider", "Model not available: $modelId", e)
            false
        }
    }

    private fun estimateTokens(messages: List<HFMessage>): Int {
        // Грубая оценка: ~4 символа на токен
        return messages.sumOf { it.content.length } / 4
    }

    private fun estimateTokens(text: String): Int {
        return text.length / 4
    }
}

@Serializable
data class HFMessage(
    val role: String,
    val content: String
)

@Serializable
data class HFChatRequest(
    val model: String,
    val messages: List<HFMessage>,
    val stream: Boolean = false
)

@Serializable
data class HFChatResponse(
    val id: String? = null,
    val `object`: String? = null,
    val created: Long? = null,
    val model: String? = null,
    val choices: List<HFChoice>,
    val usage: HFUsage? = null,
    val system_fingerprint: String? = null
)

@Serializable
data class HFChoice(
    val index: Int? = null,
    val message: HFMessage,
    val finish_reason: String? = null,
    val reasoning_content: String? = null,
    val content_filter_results: Map<String, Map<String, Boolean>>? = null
)

@Serializable
data class HFUsage(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int,
    val prompt_tokens_details: String? = null,
    val completion_tokens_details: String? = null
)

