package io.maksymdobrynin.snowflakegenerator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableConfigurationProperties
@EnableJpaRepositories(basePackages = ["io.maksymdobrynin.repository"])
@EntityScan("io.maksymdobrynin.entity")
open class SnowflakeApplication

fun main(vararg args: String) {
	runApplication<SnowflakeApplication>(*args)
}
