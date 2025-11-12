package com.arekalov.aiadventchallenge.domain.model

data class ChatResponse(
    val text: String,
    val category: String,
    val stage: String,
    val totalTokens: Int? = null,
    val situation: String = "",
    val heroes: String = "",
    val humorType: String = "",
    val metrics: ModelMetrics? = null
)

