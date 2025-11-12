package com.arekalov.aiadventchallenge.data.provider

import com.arekalov.aiadventchallenge.domain.model.ModelInfo
import com.arekalov.aiadventchallenge.domain.provider.LLMProvider

/**
 * Реестр доступных моделей
 */
class ModelRegistry(private val huggingFaceToken: String) {
    
    private val providers = mutableMapOf<String, LLMProvider>()

    init {
        registerDefaultModels()
    }

    private fun registerDefaultModels() {
        // Kimi K2 Thinking - большая модель (1T параметров)
        registerModel(
            "kimi-k2-thinking",
            HuggingFaceProvider(
                modelId = "moonshotai/Kimi-K2-Thinking:novita",
                modelName = "Kimi K2 Thinking",
                modelDescription = "1T MoE model with deep reasoning",
                apiToken = huggingFaceToken
            )
        )

        // Gemma 2 9B - средняя модель
        registerModel(
            "gemma-2-9b",
            HuggingFaceProvider(
                modelId = "google/gemma-2-9b-it",
                modelName = "Gemma 2 9B",
                modelDescription = "Google's 9B instruction-tuned model",
                apiToken = huggingFaceToken
            )
        )

        // Llama 3.2 3B - маленькая модель
        registerModel(
            "llama-3.2-3b",
            HuggingFaceProvider(
                modelId = "meta-llama/Llama-3.2-3B-Instruct",
                modelName = "Llama 3.2 3B",
                modelDescription = "Meta's compact 3B model",
                apiToken = huggingFaceToken
            )
        )
    }

    fun registerModel(id: String, provider: LLMProvider) {
        providers[id] = provider
    }

    fun getProvider(id: String): LLMProvider? {
        return providers[id]
    }

    fun getAllModels(): List<ModelInfo> {
        return listOf(
            ModelInfo(
                id = "kimi-k2-thinking",
                displayName = "🧠 Kimi K2 Thinking",
                description = "1T параметров • Глубокое мышление",
                provider = "HuggingFace",
                size = "1T"
            ),
            ModelInfo(
                id = "gemma-2-9b",
                displayName = "💎 Gemma 2 9B",
                description = "9B параметров • Google",
                provider = "HuggingFace",
                size = "9B"
            ),
            ModelInfo(
                id = "llama-3.2-3b",
                displayName = "🦙 Llama 3.2 3B",
                description = "3B параметров • Meta",
                provider = "HuggingFace",
                size = "3B"
            )
        )
    }

    fun getDefaultModelId(): String = "gemma-2-9b"
}

