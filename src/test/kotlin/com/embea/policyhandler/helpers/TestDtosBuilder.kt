package com.embea.policyhandler.helpers

import com.embea.policyhandler.dtos.InsuredPersonDto
import com.embea.policyhandler.dtos.requests.CreatePolicyRequest
import com.embea.policyhandler.dtos.requests.GetPolicyRequest
import com.embea.policyhandler.dtos.requests.UpdatePolicyRequest
import java.time.LocalDate
import java.util.UUID

object TestDtosBuilder {

    fun dummyCreatePolicyRequest(
        startDate: LocalDate = LocalDate.of(2023, 5, 5),
        insuredPersons: Set<InsuredPersonDto> = emptySet()
    ): CreatePolicyRequest {
        return CreatePolicyRequest(
            startDate = startDate,
            insuredPersons = insuredPersons
        )
    }

    fun dummyUpdatePolicyRequest(
        policyId : UUID = UUID.randomUUID(),
        effectiveDate: LocalDate = LocalDate.of(2023, 5, 5),
        insuredPersons: Set<InsuredPersonDto> = emptySet()
    ): UpdatePolicyRequest {
        return UpdatePolicyRequest(
            policyId = policyId,
            effectiveDate = effectiveDate,
            insuredPersons = insuredPersons
        )
    }

    fun dummyGetPolicyRequest(
        policyId : UUID = UUID.randomUUID(),
        requestDate: LocalDate = LocalDate.of(2023, 5, 5),
    ): GetPolicyRequest {
        return GetPolicyRequest(
            policyId = policyId,
            requestDate = requestDate
        )
    }
}