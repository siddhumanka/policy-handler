package com.embea.policyhandler.helpers

import com.embea.policyhandler.dtos.InsuredPersonDto
import com.embea.policyhandler.dtos.requests.CreatePolicyRequest
import com.embea.policyhandler.dtos.requests.UpdatePolicyRequest
import java.time.LocalDate
import java.util.UUID

object TesDtosBuilder {

    fun dummyCreatePolicyRequest(
        startDate: LocalDate = LocalDate.of(2023, 5, 5),
        insuredPersons: List<InsuredPersonDto> = emptyList()
    ): CreatePolicyRequest {
        return CreatePolicyRequest(
            startDate = startDate,
            insuredPersons = insuredPersons
        )
    }

    fun dummyUpdatePolicyRequest(
        policyId : UUID = UUID.randomUUID(),
        effectiveDate: LocalDate = LocalDate.of(2023, 5, 5),
        insuredPersons: List<InsuredPersonDto> = emptyList()
    ): UpdatePolicyRequest {
        return UpdatePolicyRequest(
            policyId = policyId,
            effectiveDate = effectiveDate,
            insuredPersons = insuredPersons
        )
    }
}