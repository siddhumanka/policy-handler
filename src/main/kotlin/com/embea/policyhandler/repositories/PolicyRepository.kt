package com.embea.policyhandler.repositories

import com.embea.policyhandler.models.Policy
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PolicyRepository : JpaRepository<Policy, UUID>