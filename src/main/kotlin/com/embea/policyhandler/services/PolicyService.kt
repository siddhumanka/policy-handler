package com.embea.policyhandler.services

import com.embea.policyhandler.dtos.requests.CreatePolicyRequest
import com.embea.policyhandler.dtos.responses.CreatePolicyResponse
import org.springframework.stereotype.Service

@Service
class PolicyService {

    fun createPolicy(request: CreatePolicyRequest): CreatePolicyResponse {

        return CreatePolicyResponse(
            startDate = request.startDate,
            insuredPersons = request.insuredPersons
                .mapIndexed { index, insuredPersonsDto -> insuredPersonsDto.copy(id = index + 1) },
            totalPremium = request.insuredPersons
                .map { it.premium }
                .reduceRight { first, next -> first + next }
        )
    }

}