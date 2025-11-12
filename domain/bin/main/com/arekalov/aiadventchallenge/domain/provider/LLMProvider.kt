package com.arekalov.aiadventchallenge.domain.provider

import com.arekalov.aiadventchallenge.domain.model.Message
import com.arekalov.aiadventchallenge.domain.model.ModelMetrics

/**
 * Интерфейс для взаимодействия с различными LLM провайдерами
 */
interface LLMProvider {
    /**
     * Отправить сообщение модели
     * @param systemPrompt системный промпт для настройки поведения модели
     * @param messages история сообщений
     * @param temperature температура для генерации (0.0-1.0)
     * @return Result с ответом модели и метриками
     */
    suspend fun sendMessage(
        systemPrompt: String,
        messages: List<Message>,
        temperature: Float = 0.7f
    ): Result<LLMResponse>

    /**
     * Получить название модели
     */
    fun getModelName(): String

    /**
     * Получить описание модели
     */
    fun getModelDescription(): String

    /**
     * Проверить доступность модели
     */
    suspend fun isAvailable(): Boolean
}

/**
 * Ответ от LLM модели с метриками
 */
data class LLMResponse(
    val text: String,
    val metrics: ModelMetrics
)

