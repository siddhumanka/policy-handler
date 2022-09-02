package com.embea.policyhandler.dtos.requests

import com.embea.policyhandler.dtos.InsuredPersonsDto
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class CreatePolicyRequest(
    @JsonFormat(pattern = "dd.MM.yyyy")
    val startDate: LocalDate,
    val insuredPersons: List<InsuredPersonsDto>
)
