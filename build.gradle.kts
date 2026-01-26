import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.6"
	id("io.spring.dependency-management") version "1.1.6"
	id("org.jlleitschuh.gradle.ktlint") version libs.versions.gradle.ktlint
	id("io.gitlab.arturbosch.detekt") version libs.versions.gradle.detekt
	kotlin("jvm") version libs.versions.kotlin
	kotlin("plugin.jpa") version libs.versions.kotlin
}

group = "io.maksymdobrynin"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(libs.kotlin.logging)
	implementation(libs.kotlin.stdlib)
	implementation(libs.kotlinx.coroutines)
	implementation(libs.kotlinx.coroutines.reactor)
	implementation(libs.springframework.boot.starter.web)
	implementation(libs.springframework.boot.starter.actuator)
	implementation(libs.springframework.boot.starter.data.jpa)
	implementation(libs.flywaydb.flyway.core)
	implementation(libs.postgresql)
	implementation(libs.kotlin.reflect)
	testImplementation(libs.junit.jupiter)
	testImplementation(libs.assertj.core)
	testImplementation(libs.kotlinx.coroutines.test)
	testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "21"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
