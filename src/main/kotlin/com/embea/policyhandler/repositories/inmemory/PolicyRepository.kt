package com.embea.policyhandler.repositories.inmemory

import com.embea.policyhandler.repositories.inmemory.models.InsuredPerson
import com.embea.policyhandler.repositories.inmemory.models.Policy
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class PolicyRepository(private val mapOfPolicies: MutableMap<UUID, Policy> = mutableMapOf()) {

    fun save(policy: Policy) {
        mapOfPolicies[policy.id] = policy
    }

    fun addInsuredPerson(policy: Policy, insuredPersons: Set<InsuredPerson>): Policy {

        val highestId = policy.insuredPersons.map { it.id }.sortedBy { it }.lastOrNull()
        val newInsuredPersons = insuredPersons.filter { it.id == null }
            .mapIndexed { index, insuredPerson -> insuredPerson.copy(id = highestId!! + index + 1) }

        val insuredPersonsToSave = insuredPersons.filter { it.id != null }.toSet() + newInsuredPersons

        val policyToSave = policy.copy(insuredPersons = insuredPersonsToSave)
        mapOfPolicies[policy.id] = policyToSave

        return policyToSave
    }

    fun getReferenceById(policyId: UUID): Policy? = mapOfPolicies[policyId]


}