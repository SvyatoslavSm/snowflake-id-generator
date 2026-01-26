package io.maksymdobrynin.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "id_info")
open class IdInfo(
	@Id
	@Column(name = "guid", nullable = false, updatable = false)
	open var guid: Long? = null,
	@Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMPTZ")
	open var createdAt: Instant = Instant.now(),
	@Column(name = "datacenter_id", nullable = false)
	open var datacenterId: Long = 0L,
	@Column(name = "worked_id", nullable = false)
	open var workedId: Long = 0L,
) {
	constructor() : this(
		guid = null,
		createdAt = Instant.now(),
		datacenterId = 0L,
		workedId = 0L,
	)
}
