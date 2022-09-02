package com.embea.policyhandler.services

import com.embea.policyhandler.dtos.InsuredPersonsDto
import com.embea.policyhandler.helpers.TesDtosBuilder.dummyCreatePolicyRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate

class PolicyServiceTest {

    private val service = PolicyService()

    @Test
    fun `createPolicy should create a new policy`() {
        val startDate = LocalDate.of(2023, 5, 5)
        val expectedFirstPremium = BigDecimal(34)
        val firstInsuredPerson =
            InsuredPersonsDto(firstName = "bat", secondName = "man", premium = expectedFirstPremium)
        val expectedSecondPremium = BigDecimal(22.5)
        val secondInsuredPerson =
            InsuredPersonsDto(firstName = "wonder", secondName = "woman", premium = expectedSecondPremium)

        val createPolicyResponse = service.createPolicy(
            dummyCreatePolicyRequest(
                startDate = startDate,
                insuredPersons = listOf(firstInsuredPerson, secondInsuredPerson)
            )
        )

        assertThat(createPolicyResponse.policyId).isNotNull
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
}