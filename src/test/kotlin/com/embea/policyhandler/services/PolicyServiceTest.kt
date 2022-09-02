package com.embea.policyhandler.services

import com.embea.policyhandler.dtos.InsuredPersonDto
import com.embea.policyhandler.helpers.TesDtosBuilder.dummyCreatePolicyRequest
import com.embea.policyhandler.helpers.TesDtosBuilder.dummyUpdatePolicyRequest
import com.embea.policyhandler.models.Policy
import com.embea.policyhandler.repositories.PolicyRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class PolicyServiceTest {

    @MockK
    private lateinit var policyRepository: PolicyRepository

    @InjectMockKs
    private lateinit var service: PolicyService

    @Test
    fun `createPolicy should create a new policy`() {
        val startDate = LocalDate.now().plusDays(22)
        val expectedFirstPremium = BigDecimal(34)
        val firstInsuredPerson =
            InsuredPersonDto(firstName = "bat", secondName = "man", premium = expectedFirstPremium)
        val expectedSecondPremium = BigDecimal(22.5)
        val secondInsuredPerson =
            InsuredPersonDto(firstName = "wonder", secondName = "woman", premium = expectedSecondPremium)
        val insuredPersons = listOf(firstInsuredPerson, secondInsuredPerson)
        val policy = Policy(
            startDate = startDate,
            insuredPersons = insuredPersons.mapIndexed { index, person -> person.toInsuredPerson(index.toLong() + 1L) }
                .toMutableList()
        )

        every { policyRepository.saveAndFlush(any()) } returns policy

        val createPolicyResponse = service.createPolicy(
            dummyCreatePolicyRequest(
                startDate = startDate,
                insuredPersons = insuredPersons
            )
        )

        assertThat(createPolicyResponse.policyId).isEqualTo(policy.id)
        assertThat(createPolicyResponse.startDate).isEqualTo(startDate)
        assertThat(createPolicyResponse.insuredPersons[0].id).isEqualTo(1)
        assertThat(createPolicyResponse.insuredPersons[0].firstName).isEqualTo("bat")
        assertThat(createPolicyResponse.insuredPersons[0].secondName).isEqualTo("man")
        assertThat(createPolicyResponse.insuredPersons[0].premium).isEqualTo(expectedFirstPremium)
        assertThat(createPolicyResponse.insuredPersons[1].id).isEqualTo(2)
        assertThat(createPolicyResponse.insuredPersons[1].firstName).isEqualTo("wonder")
        assertThat(createPolicyResponse.insuredPersons[1].secondName).isEqualTo("woman")
        assertThat(createPolicyResponse.insuredPersons[1].premium).isEqualTo(expectedSecondPremium)
        assertThat(createPolicyResponse.totalPremium).isEqualTo(expectedFirstPremium + expectedSecondPremium)
    }

    @Test
    fun `updatePolicy should update an existing policy`() {
        val startDate = LocalDate.now().plusDays(22)
        val effectiveDate = LocalDate.now().plusDays(42)
        val expectedFirstPremium = BigDecimal(34)
        val firstInsuredPerson =
            InsuredPersonDto(firstName = "bat", secondName = "man", premium = expectedFirstPremium)
        val expectedSecondPremium = BigDecimal(22.5)
        val secondInsuredPerson =
            InsuredPersonDto(firstName = "wonder", secondName = "woman", premium = expectedSecondPremium)
        val expectedThirdPremium = BigDecimal(12.5)
        val thirdInsuredPerson =
            InsuredPersonDto(firstName = "nia", secondName = "nal", premium = expectedThirdPremium)
        val insuredPersons = listOf(firstInsuredPerson, secondInsuredPerson)
        val policy = Policy(
            startDate = startDate,
            insuredPersons = insuredPersons.mapIndexed { index, person -> person.toInsuredPerson(index.toLong() + 1) }
                .toMutableList()
        )

        every { policyRepository.getReferenceById(policy.id) } returns policy

        policy.insuredPersons.removeLast()
        policy.insuredPersons.add(
            thirdInsuredPerson.copy().toInsuredPerson(3)
        )
        every { policyRepository.save(any()) } returns policy.copy(
            insuredPersons = policy.insuredPersons
        )

        val updatePolicyResponse = service.updatePolicy(
            dummyUpdatePolicyRequest(
                policyId = policy.id,
                effectiveDate = effectiveDate,
                insuredPersons = mutableListOf(firstInsuredPerson.copy(id = 1L), thirdInsuredPerson)
            )
        )

        assertThat(updatePolicyResponse.policyId).isEqualTo(policy.id)
        assertThat(updatePolicyResponse.effectiveDate).isEqualTo(effectiveDate)
        assertThat(updatePolicyResponse.insuredPersons[0].id).isEqualTo(1)
        assertThat(updatePolicyResponse.insuredPersons[0].firstName).isEqualTo("bat")
        assertThat(updatePolicyResponse.insuredPersons[0].secondName).isEqualTo("man")
        assertThat(updatePolicyResponse.insuredPersons[0].premium).isEqualTo(expectedFirstPremium)
        assertThat(updatePolicyResponse.insuredPersons[1].id).isEqualTo(3)
        assertThat(updatePolicyResponse.insuredPersons[1].firstName).isEqualTo("nia")
        assertThat(updatePolicyResponse.insuredPersons[1].secondName).isEqualTo("nal")
        assertThat(updatePolicyResponse.insuredPersons[1].premium).isEqualTo(expectedThirdPremium)
        assertThat(updatePolicyResponse.totalPremium).isEqualTo(expectedFirstPremium + expectedThirdPremium)
    }
}