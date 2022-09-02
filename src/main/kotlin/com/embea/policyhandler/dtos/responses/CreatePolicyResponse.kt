package com.embea.policyhandler.dtos.responses

import com.embea.policyhandler.dtos.InsuredPersonsDto
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class CreatePolicyResponse(
    val policyId: UUID = UUID.randomUUID(),
    val startDate: LocalDate,
    val insuredPersons: List<InsuredPersonsDto>,
    val totalPremium: BigDecimal
)