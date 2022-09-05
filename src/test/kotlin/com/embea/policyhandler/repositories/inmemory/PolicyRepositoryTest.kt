package com.embea.policyhandler.repositories.inmemory

import com.embea.policyhandler.repositories.inmemory.models.InsuredPerson
import com.embea.policyhandler.repositories.inmemory.models.Policy
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

class PolicyRepositoryTest {
    private val mapOfPolicies =
        mutableMapOf<UUID, Policy>()

    private val policyRepository = PolicyRepository(mapOfPolicies)

    @BeforeEach
    internal fun setUp() {
        mapOfPolicies.clear()
    }

    @Test
    fun `save should return save and return saved Policy`() {

        val policyId = UUID.randomUUID()
        val policy = Policy(policyId, LocalDate.now(), insuredPersons = emptySet())

        val savedPolicy = policyRepository.save(policy)

        assertThat(savedPolicy).isEqualTo(policy)
        assertThat(mapOfPolicies[policyId]).isNotNull
        assertThat(mapOfPolicies[policyId]).isEqualTo(policy)
    }

    @Test
    fun `getReferenceById should return null if policy doesn't exist`() {
        val policyId = UUID.randomUUID()

        val savedPolicy = policyRepository.getReferenceById(policyId)

        assertThat(savedPolicy).isNull()
    }

    @Test
    fun `addInsuredPerson should add new insured persons with new Ids and updated data also removing unprovided person`() {
        val policyId = UUID.randomUUID()
        val policy =
            Policy(
                policyId, LocalDate.now(), insuredPersons = setOf(
                    InsuredPerson(id = 1L, "", "", BigDecimal.ZERO),
                    InsuredPerson(id = 2L, "", "", BigDecimal.ZERO)
                )
            )

        mapOfPolicies[policyId] = policy
        val savedPolicy = policyRepository.addInsuredPerson(
            policy,
            setOf(
                InsuredPerson(id = 2L, firstName = "new", secondName = "", premium = BigDecimal.TEN),
                InsuredPerson(firstName = "", secondName = "", premium = BigDecimal.TEN)
            )
        )

        assertThat(savedPolicy).isNotNull
        assertThat(savedPolicy.insuredPersons.size).isEqualTo(2)
        assertThat(savedPolicy.insuredPersons.find { it.id == 2L }!!.firstName).isEqualTo("new")
        assertThat(savedPolicy.insuredPersons.map { it.id }).contains(3L)
    }

}