# Build stage
# Подключение JDK фиксированной версии и создание ступени
FROM eclipse-temurin:21-jdk-jammy as build
WORKDIR /app

# Файлы необходимы исключительно для сборки
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle gradle

# Скачивание зависимостей проекта
RUN ./gradlew dependencies || return 0

# Копирование исходных файлов (код)
COPY src src
# Сборка проекта
RUN ./gradlew clean build -x test -x ktlintKotlinScriptCheck -x ktlintTestSourceSetCheck -x ktlintMainSourceSetCheck

# Runtime stage
# Подключение JRE фиксированной версии
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Создание пользования с минимальными правами
RUN useradd --system --create-home --uid 1001 appuser
USER appuser

# Выполнение команд от лица пользования с минимальными правами
COPY --chown=appuser:appuser --from=build /app/build/libs/*-SNAPSHOT.jar app.jar

# Обозначаем, что приложерие внутри образа будет работать на порте 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
