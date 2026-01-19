package io.maksymdobrynin.repository

import io.maksymdobrynin.entity.IdInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IdInfoRepository : JpaRepository<IdInfo, Long>
