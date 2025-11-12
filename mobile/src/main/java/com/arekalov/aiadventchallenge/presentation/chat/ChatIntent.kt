package com.arekalov.aiadventchallenge.presentation.chat

sealed interface ChatIntent {
    data class SendMessage(val text: String) : ChatIntent
    data class UpdateInputText(val text: String) : ChatIntent
    data class UpdateTemperature(val temperature: Float) : ChatIntent
    data class SelectModel(val modelId: String) : ChatIntent
    data object ToggleSettings : ChatIntent
    data object ClearError : ChatIntent
}

