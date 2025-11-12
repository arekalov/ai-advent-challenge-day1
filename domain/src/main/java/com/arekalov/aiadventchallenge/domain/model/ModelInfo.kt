package com.arekalov.aiadventchallenge.domain.model

/**
 * Информация о доступной модели
 */
data class ModelInfo(
    val id: String,
    val displayName: String,
    val description: String,
    val provider: String, // "HuggingFace", "Yandex", etc.
    val size: String, // "3B", "9B", "1T", etc.
    val isAvailable: Boolean = true
)

