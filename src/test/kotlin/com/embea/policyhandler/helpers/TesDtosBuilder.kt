package com.embea.policyhandler.helpers

import com.embea.policyhandler.dtos.InsuredPersonsDto
import com.embea.policyhandler.dtos.requests.CreatePolicyRequest
import java.time.LocalDate

object TesDtosBuilder {

    fun dummyCreatePolicyRequest(
        startDate: LocalDate = LocalDate.of(2023, 5, 5),
        insuredPersons: List<InsuredPersonsDto> = emptyList()
    ): CreatePolicyRequest {
        return CreatePolicyRequest(
            startDate = startDate,
            insuredPersons = insuredPersons
        )
    }
}