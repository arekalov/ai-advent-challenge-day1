package com.arekalov.aiadventchallenge.data.di

import com.arekalov.aiadventchallenge.core.di.AppScope
import com.arekalov.aiadventchallenge.data.provider.ModelRegistry
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HuggingFaceToken

@Module
abstract class LLMProviderModule {
    
    companion object {
        @Provides
        @AppScope
        @HuggingFaceToken
        fun provideHuggingFaceToken(): String {
            // Токен загружается из local.properties через BuildConfig
            return com.arekalov.aiadventchallenge.data.BuildConfig.HUGGING_FACE_TOKEN
        }

        @Provides
        @AppScope
        fun provideModelRegistry(@HuggingFaceToken token: String): ModelRegistry {
            return ModelRegistry(token)
        }
    }
}

