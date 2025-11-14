package com.arekalov.aiadventchallenge.domain.repository

import com.arekalov.aiadventchallenge.domain.model.ChatRequest
import com.arekalov.aiadventchallenge.domain.model.ChatResponse
import com.arekalov.aiadventchallenge.domain.model.Message

interface ChatRepository {
    suspend fun sendMessage(request: ChatRequest): Result<ChatResponse>
    suspend fun compressHistory(messages: List<Message>): Result<Message> // Day 8: Сжатие истории
}

