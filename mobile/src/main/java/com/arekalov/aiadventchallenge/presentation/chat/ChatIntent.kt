package com.arekalov.aiadventchallenge.presentation.chat

sealed interface ChatIntent {
    data class SendMessage(val text: String) : ChatIntent
    data class UpdateInputText(val text: String) : ChatIntent
    data class UpdateTemperature(val temperature: Float) : ChatIntent
    data class SelectModel(val modelId: String) : ChatIntent
    data object ToggleSettings : ChatIntent
    data object ClearError : ChatIntent
    data object ToggleTokenTestMode : ChatIntent
    data class SendTokenTest(val testType: TokenTestType) : ChatIntent
}

enum class TokenTestType {
    SHORT,    // Короткий запрос (~100 токенов)
    MEDIUM,   // Средний запрос (~1000 токенов)
    LONG,     // Длинный запрос (~4000 токенов)
    OVERFLOW  // Превышающий лимит (~10000 токенов)
}

