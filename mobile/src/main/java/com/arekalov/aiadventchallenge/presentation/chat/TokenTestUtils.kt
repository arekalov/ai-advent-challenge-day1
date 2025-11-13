package com.arekalov.aiadventchallenge.presentation.chat

/**
 * Утилиты для тестирования работы с токенами
 */
object TokenTestUtils {
    
    /**
     * Короткий запрос (~100-150 токенов)
     */
    fun getShortRequest(): String {
        return "Расскажи короткий анекдот про программиста"
    }
    
    /**
     * Средний запрос (~1000-1500 токенов)
     */
    fun getMediumRequest(): String {
        return buildString {
            append("Создай анекдот на основе следующей развернутой ситуации:\n\n")
            append("Программист работал над сложным проектом уже несколько месяцев. ")
            append("Это был веб-сервис для управления интернет-магазином с множеством микросервисов: ")
            append("авторизация, каталог товаров, корзина, платежи, доставка, аналитика. ")
            append("Каждый сервис работал на своем стеке технологий: Python с Django, ")
            append("Node.js с Express, Java Spring Boot, Go для высоконагруженных компонентов. ")
            append("База данных была распределенной: PostgreSQL для основных данных, ")
            append("MongoDB для логов, Redis для кэширования, Elasticsearch для поиска. ")
            append("Вся инфраструктура разворачивалась в Kubernetes с автомасштабированием, ")
            append("мониторингом через Prometheus и Grafana, логированием в ELK стек. ")
            append("\n\n")
            append("Однажды в пятницу вечером, когда программист уже собирался домой, ")
            append("пришел срочный запрос от менеджера проекта: нужно добавить новую фичу ")
            append("к понедельнику. Фича казалась простой - добавить возможность оставлять отзывы ")
            append("на товары. Но как обычно, дьявол крылся в деталях: ")
            append("отзывы должны поддерживать фото и видео, модерацию через ML-модель, ")
            append("автоматическую проверку на токсичность, возможность реакций (лайки, дизлайки), ")
            append("ответы продавцов, систему рейтингов влияющую на позиции в поиске, ")
            append("и все это с реал-тайм уведомлениями через WebSocket. ")
            append("\n\n")
            append("Программист посмотрел на календарь, вздохнул, открыл IDE и начал ")
            append("проектировать архитектуру новой подсистемы. Ему предстояло: ")
            append("спроектировать схему БД, написать миграции, реализовать REST API, ")
            append("интегрировать ML-модель для модерации, настроить очереди сообщений через RabbitMQ, ")
            append("написать воркеры для обработки изображений и видео, ")
            append("реализовать WebSocket сервер для уведомлений, обновить фронтенд на React, ")
            append("написать юнит-тесты, интеграционные тесты, подготовить докер-образы, ")
            append("обновить CI/CD пайплайны, написать документацию для API. ")
            append("\n\n")
            append("Создай смешной анекдот про то, что случилось дальше. ")
            append("Используй темы: дедлайны, переработки, багфиксы, техдолг, code review, и современные технологии.")
        }
    }
    
    /**
     * Длинный запрос (~4000-5000 токенов)
     */
    fun getLongRequest(): String {
        return buildString {
            append("ДЕТАЛЬНОЕ ТЕХНИЧЕСКОЕ ЗАДАНИЕ НА СОЗДАНИЕ АНЕКДОТА\n\n")
            append("=" .repeat(80))
            append("\n\n")
            
            append("1. ОБЩАЯ ИНФОРМАЦИЯ О ПРОЕКТЕ\n")
            append("-".repeat(80))
            append("\n")
            append("Проект: Корпоративная система управления IT-инфраструктурой\n")
            append("Заказчик: Крупная международная корпорация в сфере финтех\n")
            append("Команда: 50+ разработчиков, 10 DevOps инженеров, 5 архитекторов, 20 QA\n")
            append("Срок разработки: 2 года (текущий прогресс: 95%)\n")
            append("Технологии: Kotlin, Java, Python, Go, React, Vue.js, TypeScript\n")
            append("Инфраструктура: AWS, GCP, on-premise серверы\n")
            append("Бюджет: $10M+ (уже превышен на 30%)\n\n")
            
            append("2. АРХИТЕКТУРА СИСТЕМЫ\n")
            append("-".repeat(80))
            append("\n")
            append("2.1. Микросервисная архитектура (150+ сервисов):\n")
            append("   - Authentication Service (OAuth2, SAML, LDAP интеграция)\n")
            append("   - User Management Service (CRUD операции, роли, права)\n")
            append("   - Asset Management Service (серверы, сети, устройства)\n")
            append("   - Monitoring Service (метрики, логи, трейсы)\n")
            append("   - Alerting Service (уведомления в Slack, email, SMS)\n")
            append("   - Incident Management (интеграция с PagerDuty, Jira)\n")
            append("   - Configuration Management (хранение конфигов, версионирование)\n")
            append("   - Deployment Service (CI/CD пайплайны, blue-green деплой)\n")
            append("   - Analytics Service (BigQuery, Apache Spark, данные в реальном времени)\n")
            append("   - Reporting Service (генерация отчетов в PDF, Excel)\n")
            append("   - API Gateway (Kong, rate limiting, аутентификация)\n")
            append("   - Service Mesh (Istio для управления трафиком)\n\n")
            
            append("2.2. Базы данных:\n")
            append("   - PostgreSQL кластер (12 нод, репликация master-slave)\n")
            append("   - MongoDB кластер (для документов и логов)\n")
            append("   - Redis кластер (кэширование, сессии, очереди)\n")
            append("   - Elasticsearch (поиск по логам, full-text search)\n")
            append("   - ClickHouse (аналитика, OLAP запросы)\n")
            append("   - Neo4j (граф зависимостей между сервисами)\n\n")
            
            append("2.3. Очереди сообщений:\n")
            append("   - Apache Kafka (основная шина данных, 10TB/день)\n")
            append("   - RabbitMQ (асинхронные задачи)\n")
            append("   - AWS SQS (интеграция с облачными сервисами)\n\n")
            
            append("2.4. Мониторинг и observability:\n")
            append("   - Prometheus (метрики)\n")
            append("   - Grafana (дашборды, 500+ панелей)\n")
            append("   - Jaeger (distributed tracing)\n")
            append("   - ELK Stack (логи)\n")
            append("   - New Relic (APM)\n")
            append("   - DataDog (инфраструктурный мониторинг)\n\n")
            
            append("3. КОНТЕКСТ СИТУАЦИИ\n")
            append("-".repeat(80))
            append("\n")
            append("Дата: Пятница, 22:00\n")
            append("Событие: Завтра в 10:00 презентация системы совету директоров\n")
            append("Проблема: За 2 часа до конца рабочего дня обнаружен критический баг\n\n")
            
            append("3.1. Описание бага:\n")
            append("При одновременном запросе от более чем 10000 пользователей система ")
            append("начинает работать некорректно. Симптомы:\n")
            append("   - Response time увеличивается с 50ms до 30 секунд\n")
            append("   - Memory leak в Authentication Service (8GB/час)\n")
            append("   - Cascade failures в зависимых сервисах\n")
            append("   - Database connection pool exhaustion\n")
            append("   - Kafka consumer lag растет до 100K сообщений\n")
            append("   - Circuit breakers начинают срабатывать\n")
            append("   - Pod restarts в Kubernetes (CrashLoopBackOff)\n\n")
            
            append("3.2. Попытки решения:\n")
            append("Команда пробовала:\n")
            append("   1. Увеличить heap size для JVM (не помогло)\n")
            append("   2. Добавить реплики сервисов (хуже не стало, но и не помогло)\n")
            append("   3. Оптимизировать SQL запросы (10% улучшение)\n")
            append("   4. Включить дополнительное кэширование (regression в другом месте)\n")
            append("   5. Rollback на предыдущую версию (баг появился 3 релиза назад)\n")
            append("   6. Позвать архитектора (он в отпуске в Тайланде)\n")
            append("   7. Искать в Stack Overflow (нашли похожую проблему, но решение не подходит)\n\n")
            
            append("3.3. Дополнительные осложнения:\n")
            append("   - Senior developer, который писал этот код, уволился 3 месяца назад\n")
            append("   - Документация устарела и не соответствует текущему коду\n")
            append("   - Code coverage тестами только 45%\n")
            append("   - В коде 15000+ строк legacy кода на deprecated библиотеках\n")
            append("   - Tech debt накоплен за 2 года разработки\n")
            append("   - Последний рефакторинг был 8 месяцев назад\n")
            append("   - В коде есть TODO комментарии 'temporary solution, fix later'\n\n")
            
            append("4. КОМАНДА В ДАННЫЙ МОМЕНТ\n")
            append("-".repeat(80))
            append("\n")
            append("   - CTO: на конференции в Сан-Франциско\n")
            append("   - Главный архитектор: отпуск, не отвечает на звонки\n")
            append("   - 3 Senior developers: онлайн, копаются в логах\n")
            append("   - 5 Middle developers: пытаются воспроизвести баг локально\n")
            append("   - 2 Junior developers: молча наблюдают в Slack\n")
            append("   - DevOps team: перезапускают pod'ы в Kubernetes\n")
            append("   - QA lead: пишет angry email о том, что надо было больше тестировать\n")
            append("   - Product Manager: в панике созванивается с директорами\n")
            append("   - CEO: спит и не знает о проблеме (никто не решается ему позвонить)\n\n")
            
            append("5. ЗАДАНИЕ\n")
            append("-".repeat(80))
            append("\n")
            append("Создай СМЕШНОЙ и ОРИГИНАЛЬНЫЙ анекдот на основе этой ситуации.\n\n")
            
            append("Требования к анекдоту:\n")
            append("   - Длина: 5-10 предложений\n")
            append("   - Стиль: программистский юмор\n")
            append("   - Обязательно использовать: технические термины, современные технологии\n")
            append("   - Желательно: неожиданная концовка (plot twist)\n")
            append("   - Тип юмора: ситуационный, с элементами абсурда\n")
            append("   - Целевая аудитория: IT-специалисты с опытом работы в больших компаниях\n\n")
            
            append("6. ПРИМЕРЫ ТОГО, ЧТО НЕ НАДО:\n")
            append("-".repeat(80))
            append("\n")
            append("   ❌ 'А потом все сломалось' (слишком банально)\n")
            append("   ❌ 'Оказалось, надо было просто перезагрузить' (заезженная шутка)\n")
            append("   ❌ 'Виноват был стажер' (неэтично)\n")
            append("   ❌ Шутки про 'работает на моей машине'\n")
            append("   ❌ Банальные шутки про Stack Overflow\n\n")
            
            append("7. ДОПОЛНИТЕЛЬНЫЙ КОНТЕКСТ ДЛЯ ВДОХНОВЕНИЯ:\n")
            append("-".repeat(80))
            append("\n")
            append("   - В компании проходит 'Неделя качества кода'\n")
            append("   - На прошлой неделе команда получила award за 'Best Architecture'\n")
            append("   - В офисе есть плакат 'X дней без production incidents' (сейчас там 0)\n")
            append("   - В прошлом году похожая ситуация привела к увольнению половины команды\n")
            append("   - Конкуренты завтра анонсируют похожий продукт\n")
            append("   - В коде есть комментарий 'NEVER TOUCH THIS CODE' именно в том месте, где баг\n\n")
        }
    }
    
    /**
     * Запрос, превышающий лимит (~10000+ токенов)
     */
    fun getOverflowRequest(): String {
        return buildString {
            // Добавляем очень длинный контекст для превышения лимита
            append("МЕГА-ДЕТАЛЬНОЕ ТЕХНИЧЕСКОЕ ЗАДАНИЕ\n\n")
            append("=".repeat(100))
            append("\n\n")
            
            // Повторяем длинный запрос несколько раз с вариациями
            repeat(3) { iteration ->
                append("\n\nВЕРСИЯ СИТУАЦИИ ${iteration + 1}\n")
                append("-".repeat(100))
                append("\n")
                append(getLongRequest())
                append("\n\n")
                
                // Добавляем еще больше контекста
                append("ДОПОЛНИТЕЛЬНЫЕ ДЕТАЛИ К ВЕРСИИ ${iteration + 1}:\n")
                append("Эта версия событий рассказана ${
                    when(iteration) {
                        0 -> "Junior разработчиком, который впервые столкнулся с такой проблемой"
                        1 -> "Senior разработчиком, который видел подобное 100 раз"
                        else -> "DevOps инженером, который устал перезапускать сервисы"
                    }
                }\n\n")
                
                // Добавляем технические логи (fake)
                append("ТЕХНИЧЕСКИЕ ЛОГИ (последние 100 строк):\n")
                repeat(50) { logLine ->
                    append("[${System.currentTimeMillis() - logLine * 1000}] ")
                    append("ERROR - Service ${logLine % 10}: ")
                    append("${listOf("OutOfMemoryError", "ConnectionTimeout", "NullPointerException", "CircuitBreakerOpen").random()} ")
                    append("at line ${(logLine * 137) % 9999}\n")
                }
                append("\n")
            }
            
            append("\n\nТЕПЕРЬ, УЧИТЫВАЯ ВСЮ ЭТУ ОГРОМНУЮ ИНФОРМАЦИЮ, ")
            append("СОЗДАЙ АНЕКДОТ КОТОРЫЙ СУММИРУЕТ ВСЕ ВЕРСИИ СОБЫТИЙ!")
        }
    }
    
    /**
     * Получить описание теста
     */
    fun getTestDescription(testType: TokenTestType): String {
        return when (testType) {
            TokenTestType.SHORT -> "Короткий запрос (~100-150 токенов)"
            TokenTestType.MEDIUM -> "Средний запрос (~1000-1500 токенов)"
            TokenTestType.LONG -> "Длинный запрос (~4000-5000 токенов)"
            TokenTestType.OVERFLOW -> "Превышение лимита (~10000+ токенов)"
        }
    }
    
    /**
     * Получить ожидаемое поведение
     */
    fun getExpectedBehavior(testType: TokenTestType): String {
        return when (testType) {
            TokenTestType.SHORT -> "✅ Быстрая обработка, минимальное использование токенов"
            TokenTestType.MEDIUM -> "✅ Нормальная обработка, средний расход токенов"
            TokenTestType.LONG -> "⚠️ Медленная обработка, высокий расход токенов"
            TokenTestType.OVERFLOW -> "❌ Вероятна ошибка или обрезание контекста"
        }
    }
}

