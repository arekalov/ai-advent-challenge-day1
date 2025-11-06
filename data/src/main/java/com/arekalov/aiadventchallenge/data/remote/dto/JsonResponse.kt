package com.arekalov.aiadventchallenge.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class JsonResponse(
    val response: String,
    val category: String,
    val stage: String,
    val situation: String,
    val heroes: String,
    val humor_type: String
)

