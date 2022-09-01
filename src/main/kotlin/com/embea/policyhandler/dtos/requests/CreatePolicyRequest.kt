package com.embea.policyhandler.dtos.requests

import com.embea.policyhandler.dtos.InsuredPersons
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class CreatePolicyRequest(
    @JsonFormat(pattern="dd.MM.yyyy")
    private val startDate: LocalDate,
    private val insuredPersons: List<InsuredPersons>
)
