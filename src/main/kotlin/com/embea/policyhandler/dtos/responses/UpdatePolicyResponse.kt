package com.embea.policyhandler.dtos.responses

import com.embea.policyhandler.dtos.InsuredPersonDto
import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.validation.annotation.Validated
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Validated
data class UpdatePolicyResponse(
    val policyId: UUID = UUID.randomUUID(),
    @JsonFormat(pattern = "dd.MM.yyyy")
    val effectiveDate: LocalDate,
    val insuredPersons: List<InsuredPersonDto>,
    val totalPremium: BigDecimal
)