package com.embea.policyhandler.dtos.requests

import com.embea.policyhandler.dtos.InsuredPersonDto
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import javax.validation.constraints.Future

data class CreatePolicyRequest(
    @field:Future(message = "startDate should be in future always")
    @JsonFormat(pattern = "dd.MM.yyyy")
    val startDate: LocalDate,
    val insuredPersons: Set<InsuredPersonDto>
)
