package com.arekalov.aiadventchallenge.domain.model

data class Message(
    val id: String,
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val category: String,
    val totalTokens: Int? = null,
    val metrics: ModelMetrics? = null, // Метрики для сообщений от модели
    val isSummary: Boolean = false, // Флаг, что это сжатое сообщение
    val summarizedCount: Int = 0 // Количество сообщений, которые были сжаты в это summary
)

