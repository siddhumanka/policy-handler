package com.embea.policyhandler.services

import com.embea.policyhandler.dtos.exceptions.PolicyNotFoundException
import com.embea.policyhandler.dtos.requests.CreatePolicyRequest
import com.embea.policyhandler.dtos.requests.UpdatePolicyRequest
import com.embea.policyhandler.dtos.responses.CreatePolicyResponse
import com.embea.policyhandler.dtos.responses.UpdatePolicyResponse
import com.embea.policyhandler.repositories.inmemory.PolicyRepository
import com.embea.policyhandler.repositories.inmemory.models.Policy
import org.springframework.stereotype.Service

@Service
class PolicyService(private val policyRepository: PolicyRepository) {

    fun createPolicy(request: CreatePolicyRequest): CreatePolicyResponse {

        val insuredPersons =
            request.insuredPersons.mapIndexed { index, insuredPersonDto ->
                insuredPersonDto.toInsuredPerson().copy(id = index + 1L)
            }.toSet()

        val policy =
            Policy(startDate = request.startDate, insuredPersons = insuredPersons)

        policyRepository.save(policy)

        return CreatePolicyResponse(
            policyId = policy.id,
            startDate = policy.startDate,
            insuredPersons = policy.insuredPersons.map { it.toDto() },
            totalPremium = policy.insuredPersons.map { it.premium }
                .reduceRight { first, next -> first + next }
        )
    }

    fun updatePolicy(request: UpdatePolicyRequest): UpdatePolicyResponse {
        val policy = policyRepository.getReferenceById(request.policyId) ?: throw PolicyNotFoundException()
        val updatedPolicy = policy.copy(effectiveDate = request.effectiveDate)

        val savedPolicy = policyRepository.addInsuredPerson(
            updatedPolicy,
            request.insuredPersons.map { it.toInsuredPerson() }.toSet()
        )

        return UpdatePolicyResponse(
            policyId = request.policyId,
            effectiveDate = request.effectiveDate,
            insuredPersons = savedPolicy.insuredPersons.map { it.toDto() },
            totalPremium = savedPolicy.insuredPersons.map { it.premium }
                .reduceRight { first, next -> first + next }
        )
    }

}