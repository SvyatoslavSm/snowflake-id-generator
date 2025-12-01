package io.maksymdobrynin.snowflakegenerator

data class KubernetesSettings(
	var podName: String? = null,
	var nodeName: String? = null,
)
