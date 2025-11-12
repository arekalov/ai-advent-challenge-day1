# 🔥 День 6: Сравнение версий моделей

## 📋 Задание

- Вызвать один и тот же запрос на трёх разных моделях (из начала, середины и конца списка HuggingFace)
- Замерить время ответа, количество токенов и итоговую стоимость (если модель платная)
- Сравнить качество ответов

## 🎯 Выбранные модели

### 1. 🧠 **Kimi K2 Thinking** (1T параметров)

- **Провайдер:** HuggingFace
- **Модель:** `moonshotai/Kimi-K2-Thinking`
- **Размер:** 1 триллион параметров (MoE: 32B активных)
- **Особенности:** Глубокое мышление, инструментальные вызовы, 256K контекст

### 2. 💎 **Gemma 2 9B** (9B параметров)

- **Провайдер:** HuggingFace
- **Модель:** `google/gemma-2-9b-it`
- **Размер:** 9 миллиардов параметров
- **Особенности:** Instruction-tuned от Google, оптимизирована для диалогов

### 3. 🦙 **Llama 3.2 3B** (3B параметров)

- **Провайдер:** HuggingFace
- **Модель:** `meta-llama/Llama-3.2-3B-Instruct`
- **Размер:** 3 миллиарда параметров
- **Особенности:** Компактная модель от Meta, быстрая

## 🏗️ Архитектура решения

### Pluggable LLM Provider System

```
domain/
├── LLMProvider (interface)          # Единый интерфейс для всех моделей
│   ├── sendMessage()                # Отправка запроса
│   ├── getModelName()               # Название модели
│   └── getModelDescription()        # Описание
└── ModelMetrics (data class)        # Метрики производительности

data/
├── providers/
│   ├── HuggingFaceProvider          # Реализация для HF API
│   └── ModelRegistry                # Реестр доступных моделей
└── repository/
    └── ChatRepositoryImpl           # Динамически выбирает провайдер

presentation/
├── ModelSelector (UI)               # Dropdown для выбора модели
└── MetricsDisplay (UI)              # Отображение метрик в сообщениях
```

### Ключевые преимущества

✅ **Гибкость** - легко добавить новые модели  
✅ **Единообразие** - все модели работают через один интерфейс  
✅ **Метрики** - автоматический сбор времени ответа и токенов  
✅ **UI** - удобный выбор модели и отображение метрик

## 📊 Сравнение моделей

| Параметр            | Kimi K2 (1T)    | Gemma 2 (9B)  | Llama 3.2 (3B)  |
| ------------------- | --------------- | ------------- | --------------- |
| **Размер**          | 1T (32B active) | 9B            | 3B              |
| **Скорость** \*     | Средняя         | Быстрая       | Очень быстрая   |
| **Качество** \*     | ⭐⭐⭐⭐⭐      | ⭐⭐⭐⭐      | ⭐⭐⭐          |
| **Креативность** \* | ⭐⭐⭐⭐⭐      | ⭐⭐⭐⭐      | ⭐⭐⭐          |
| **Контекст**        | 256K            | 8K            | 128K            |
| **Стоимость**       | Free (HF)       | Free (HF)     | Free (HF)       |
| **Use Case**        | Сложные задачи  | Универсальная | Быстрые запросы |

\* _Оценки могут варьироваться в зависимости от задачи_

## 🎨 UI Features

### 1. Селектор модели

```kotlin
ModelSelector(
    availableModels = registry.getAllModels(),
    selectedModelId = state.selectedModelId,
    onModelSelected = { modelId ->
        viewModel.handleIntent(ChatIntent.SelectModel(modelId))
    }
)
```

**Отображение:**

- 🧠 Kimi K2 Thinking - 1T параметров • Глубокое мышление
- 💎 Gemma 2 9B - 9B параметров • Google
- 🦙 Llama 3.2 3B - 3B параметров • Meta

### 2. Метрики в сообщениях

Каждое сообщение от модели теперь показывает:

- ⏱️ **Время ответа:** "2.5s" / "350ms"
- 🔢 **Токены:** "150t" (общее количество)
- 📊 **Категория:** "Генерация_способ_1"

## 💻 Техническая реализация

### 1. LLMProvider Interface

```kotlin
interface LLMProvider {
    suspend fun sendMessage(
        systemPrompt: String,
        messages: List<Message>,
        temperature: Float = 0.7f
    ): Result<LLMResponse>

    fun getModelName(): String
    fun getModelDescription(): String
    suspend fun isAvailable(): Boolean
}

data class LLMResponse(
    val text: String,
    val metrics: ModelMetrics
)

data class ModelMetrics(
    val responseTimeMs: Long,
    val inputTokens: Int,
    val outputTokens: Int,
    val totalTokens: Int,
    val modelName: String,
    val estimatedCost: Double = 0.0
)
```

### 2. HuggingFace Provider

```kotlin
class HuggingFaceProvider(
    private val modelId: String,
    private val modelName: String,
    private val modelDescription: String,
    private val apiToken: String
) : LLMProvider {

    override suspend fun sendMessage(...): Result<LLMResponse> {
        val startTime = System.currentTimeMillis()

        val response = client.post(
            "https://api-inference.huggingface.co/models/$modelId/v1/chat/completions"
        ) {
            // Формат OpenAI-compatible API
            setBody(HFChatRequest(
                model = modelId,
                messages = hfMessages,
                temperature = temperature.toDouble(),
                max_tokens = 2048
            ))
        }

        val responseTimeMs = System.currentTimeMillis() - startTime
        val metrics = ModelMetrics(
            responseTimeMs = responseTimeMs,
            inputTokens = usage?.prompt_tokens ?: estimateTokens(hfMessages),
            outputTokens = usage?.completion_tokens ?: estimateTokens(text),
            modelName = modelName
        )

        return LLMResponse(text, metrics)
    }
}
```

### 3. Model Registry

```kotlin
class ModelRegistry(private val huggingFaceToken: String) {

    private val providers = mutableMapOf<String, LLMProvider>()

    init {
        registerDefaultModels()
    }

    private fun registerDefaultModels() {
        registerModel("kimi-k2-thinking", HuggingFaceProvider(...))
        registerModel("gemma-2-9b", HuggingFaceProvider(...))
        registerModel("llama-3.2-3b", HuggingFaceProvider(...))
    }

    fun getProvider(id: String): LLMProvider? = providers[id]
    fun getAllModels(): List<ModelInfo> = listOf(...)
}
```

### 4. Dynamic Provider Selection

```kotlin
// ChatRepositoryImpl.kt
override suspend fun sendMessage(request: ChatRequest): Result<ChatResponse> {
    // Получаем провайдер для выбранной модели
    val provider = registry.getProvider(request.modelId)
        ?: throw IllegalArgumentException("Model not found: ${request.modelId}")

    // Вызываем провайдер с нужным промптом
    val llmResponse = provider.sendMessage(
        systemPrompt = getSystemPrompt(currentStage),
        messages = messages,
        temperature = request.temperature
    ).getOrThrow()

    // Метрики автоматически собраны провайдером
    return parseResponse(llmResponse.text, llmResponse.metrics)
}
```

## 🔧 Конфигурация

### local.properties

```properties
HUGGING_FACE_TOKEN=your_token_here
```

### Gradle BuildConfig

```kotlin
buildConfigField("String", "HUGGING_FACE_TOKEN",
    "\"${properties.getProperty("HUGGING_FACE_TOKEN", "")}\"")
```

### Dagger DI

```kotlin
@Module
abstract class LLMProviderModule {
    companion object {
        @Provides
        @AppScope
        fun provideModelRegistry(@HuggingFaceToken token: String): ModelRegistry {
            return ModelRegistry(token)
        }
    }
}
```

## 🎯 Выводы

### 1. Архитектурные преимущества

- **Pluggable design** позволяет легко добавлять новые модели
- **Единый интерфейс** упрощает работу с разными провайдерами
- **Автоматический сбор метрик** для всех моделей

### 2. Практические наблюдения

- **Крупные модели (K2)** дают лучшее качество, но медленнее
- **Средние модели (Gemma)** - оптимальный баланс
- **Маленькие модели (Llama 3.2)** - быстрые, но менее креативные

### 3. Рекомендации по выбору

| Задача              | Рекомендуемая модель |
| ------------------- | -------------------- |
| Сложные рассуждения | Kimi K2 Thinking     |
| Универсальный чат   | Gemma 2 9B           |
| Быстрые ответы      | Llama 3.2 3B         |
| Креативный контент  | Kimi K2 Thinking     |
| Резюмирование       | Gemma 2 9B           |
| Классификация       | Llama 3.2 3B         |

## 🚀 Как использовать

1. **Добавьте токен HuggingFace в `local.properties`:**

   ```
   HUGGING_FACE_TOKEN=hf_xxxxxxxxxxxxx
   ```

2. **Запустите приложение**

3. **Выберите модель** в dropdown меню над полем ввода

4. **Создайте анекдот** как обычно

5. **Сравните результаты:**
   - Время ответа отображается под каждым сообщением
   - Количество токенов показано рядом с временем
   - Переключайте модели и сравнивайте качество

## 📚 Ресурсы

- [Kimi K2 Thinking](https://huggingface.co/moonshotai/Kimi-K2-Thinking)
- [Gemma 2 9B](https://huggingface.co/google/gemma-2-9b-it)
- [Llama 3.2 3B](https://huggingface.co/meta-llama/Llama-3.2-3B-Instruct)
- [HuggingFace Inference API](https://huggingface.co/docs/api-inference/index)

## 🔄 Совместимость с Day 4 и Day 5

✅ **Все 4 reasoning подхода работают с любой моделью**  
✅ **Temperature control сохранён**  
✅ **Multi-agent система адаптирована под pluggable architecture**

Каждая модель использует одни и те же промпты для reasoning, но может интерпретировать их по-разному в зависимости от своих capabilities.
