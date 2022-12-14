package com.embea.policyhandler.services

import com.embea.policyhandler.dtos.InsuredPersonDto
import com.embea.policyhandler.dtos.exceptions.PolicyNotFoundException
import com.embea.policyhandler.helpers.TestDtosBuilder.dummyCreatePolicyRequest
import com.embea.policyhandler.helpers.TestDtosBuilder.dummyGetPolicyRequest
import com.embea.policyhandler.helpers.TestDtosBuilder.dummyUpdatePolicyRequest
import com.embea.policyhandler.repositories.inmemory.PolicyRepository
import com.embea.policyhandler.repositories.inmemory.models.InsuredPerson
import com.embea.policyhandler.repositories.inmemory.models.Policy
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
        val insuredPersonDtos = setOf(firstInsuredPerson, secondInsuredPerson)
        val insuredPersons = setOf(firstInsuredPerson.toInsuredPerson(), secondInsuredPerson.toInsuredPerson())
        val policy = Policy(
            startDate = startDate,
            insuredPersons = insuredPersons
        )
        every { policyRepository.save(any()) } returns policy

        val createPolicyResponse = service.createPolicy(
            dummyCreatePolicyRequest(startDate = startDate, insuredPersons = insuredPersonDtos)
        )

        assertThat(createPolicyResponse.policyId).isEqualTo(policy.id)
        assertThat(createPolicyResponse.insuredPersons).isNotEmpty
        assertThat(createPolicyResponse.startDate).isEqualTo(startDate)
    }

    @Test
    fun `updatePolicy should throw PolicyNotFoundException`() {
        every { policyRepository.getReferenceById(any()) } throws PolicyNotFoundException()

        assertThrows<PolicyNotFoundException> { service.updatePolicy(dummyUpdatePolicyRequest()) }
    }

    @Test
    fun `updatePolicy should update an existing policy`() {
        val startDate = LocalDate.now().plusDays(22)
        val effectiveDate = LocalDate.now().plusDays(42)
        val expectedFirstPremium = BigDecimal(34)
        val firstInsuredPerson =
            InsuredPerson(firstName = "bat", secondName = "man", premium = expectedFirstPremium)
        val expectedSecondPremium = BigDecimal(22.5)
        val secondInsuredPerson =
            InsuredPerson(firstName = "wonder", secondName = "woman", premium = expectedSecondPremium)
        val expectedThirdPremium = BigDecimal(12.5)
        val thirdInsuredPerson =
            InsuredPersonDto(firstName = "nia", secondName = "nal", premium = expectedThirdPremium)
        val insuredPersons = mutableSetOf(firstInsuredPerson, secondInsuredPerson)
        val policy = Policy(
            startDate = startDate,
            insuredPersons = insuredPersons
        )

        val updatedPolicy = Policy(
            id = policy.id,
            startDate = startDate,
            effectiveDate = effectiveDate,
            insuredPersons = insuredPersons + thirdInsuredPerson.toInsuredPerson()
        )

        every { policyRepository.getReferenceById(policy.id) } returns policy
        every { policyRepository.addInsuredPerson(any(), any()) } returns updatedPolicy

        val updatePolicyResponse =
            service.updatePolicy(
                dummyUpdatePolicyRequest(
                    policyId = policy.id,
                    effectiveDate = effectiveDate,
                    setOf(thirdInsuredPerson)
                )
            )

        val policyArgumentCaptor = slot<Policy>()
        verify { policyRepository.addInsuredPerson(capture(policyArgumentCaptor), any()) }

        assertThat(policyArgumentCaptor.captured.id).isEqualTo(policy.id)
        assertThat(policyArgumentCaptor.captured.effectiveDate).isEqualTo(effectiveDate)
        assertThat(updatePolicyResponse.policyId).isEqualTo(policy.id)
        assertThat(updatePolicyResponse.insuredPersons.map { it.firstName }).contains("nia")
    }

    @Test
    fun `getPolicy should throw PolicyNotFoundException`() {
        every { policyRepository.getReferenceById(any()) } throws PolicyNotFoundException()

        assertThrows<PolicyNotFoundException> { service.getPolicy(dummyGetPolicyRequest()) }
    }

    @Test
    fun `getPolicy should return policy with request date`() {
        val firstInsuredPerson =
            InsuredPerson(firstName = "bat", secondName = "man", premium = BigDecimal.TEN)
        val requestDate = LocalDate.now().plusDays(22)
        val policy = Policy(
            startDate = requestDate,
            insuredPersons = setOf(firstInsuredPerson)
        )

        val updatedPolicy = policy.copy(requestDate = requestDate)

        every { policyRepository.getReferenceById(policy.id) } returns policy
        every { policyRepository.save(any()) } returns updatedPolicy

        val getPolicyResponse =
            service.getPolicy(dummyGetPolicyRequest(policyId = policy.id, requestDate = requestDate))

        val policyArgumentCaptor = slot<Policy>()
        verify { policyRepository.save(capture(policyArgumentCaptor)) }

        assertThat(policyArgumentCaptor.captured.id).isEqualTo(policy.id)
        assertThat(policyArgumentCaptor.captured.requestDate).isEqualTo(requestDate)
        assertThat(getPolicyResponse.policyId).isEqualTo(policy.id)
    }
}