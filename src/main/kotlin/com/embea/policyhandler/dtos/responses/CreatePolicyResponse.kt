package com.embea.policyhandler.dtos.responses

import com.embea.policyhandler.dtos.InsuredPersonDto
import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class CreatePolicyResponse(
    val policyId: UUID,
    @JsonFormat(pattern = "dd.MM.yyyy")
    val startDate: LocalDate,
    val insuredPersons: Set<InsuredPersonDto>,
    val totalPremium: BigDecimal
)