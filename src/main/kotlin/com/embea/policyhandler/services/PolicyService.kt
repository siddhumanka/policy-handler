package com.embea.policyhandler.services

import com.embea.policyhandler.dtos.requests.CreatePolicyRequest
import com.embea.policyhandler.dtos.requests.UpdatePolicyRequest
import com.embea.policyhandler.dtos.responses.CreatePolicyResponse
import com.embea.policyhandler.dtos.responses.UpdatePolicyResponse
import com.embea.policyhandler.models.Policy
import com.embea.policyhandler.repositories.PolicyRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class PolicyService(private val policyRepository: PolicyRepository) {

    fun createPolicy(request: CreatePolicyRequest): CreatePolicyResponse {
        val policy =
            Policy(
                startDate = request.startDate,
                insuredPersons = request.insuredPersons
                    .mapIndexed { index, person -> person.toInsuredPerson(index.toLong() + 1L) }
                    .toMutableList()
            )

        val savedPolicy = policyRepository.saveAndFlush(policy)

        return CreatePolicyResponse(
            policyId = savedPolicy.id,
            startDate = savedPolicy.startDate,
            insuredPersons = savedPolicy.insuredPersons.map { it.toDto() },
            totalPremium = savedPolicy.insuredPersons.map { it.premium }
                .reduceRight { first, next -> first + next }
        )
    }

    @Transactional
    fun updatePolicy(request: UpdatePolicyRequest): UpdatePolicyResponse {
        val policy = policyRepository.getReferenceById(request.policyId)
        val currentInsuredPersons = policy.insuredPersons
        val newInsuredPersons = request.insuredPersons.filter { it.id == null }
            .mapIndexed { index, person -> person.copy(id = (index + 1 + currentInsuredPersons.size).toLong()) }
        val allInsuredPersonsFromRequest = request.insuredPersons.filter { it.id != null } + newInsuredPersons

        val savedPolicy = policyRepository.save(
            policy.copy(
                effectiveDate = request.effectiveDate,
                insuredPersons = allInsuredPersonsFromRequest.map { it.toInsuredPerson(it.id!!) }.toMutableList()
            )
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