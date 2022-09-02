package com.embea.policyhandler.controllers

import com.embea.policyhandler.dtos.exceptions.InvalidStartDateException
import com.embea.policyhandler.dtos.responses.CreatePolicyResponse
import com.embea.policyhandler.helpers.TesDtosBuilder.dummyCreatePolicyRequest
import com.embea.policyhandler.services.PolicyService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.time.LocalDate


@ExtendWith(MockKExtension::class)
class PolicyControllerTest {

    @MockK
    private lateinit var policyService: PolicyService

    @InjectMockKs
    private lateinit var controller: PolicyController

    @Test
    fun `should throw InvalidStartDateException if start date provided is in past`() {
        val startDate = LocalDate.of(1988, 5, 5)

        assertThrows<InvalidStartDateException> {
            controller.createPolicy(dummyCreatePolicyRequest(startDate = startDate, insuredPersons = emptyList()))
        }
    }

    @Test
    fun `should create a policy`() {
        val createPolicyRequest = dummyCreatePolicyRequest()

        val expectedResponse = CreatePolicyResponse(
            startDate = LocalDate.now(),
            insuredPersons = emptyList(),
            totalPremium = BigDecimal.TEN
        )
        every { policyService.createPolicy(createPolicyRequest) } returns expectedResponse

        val actualResponse = controller.createPolicy(
            createPolicyRequest
        )

        assertThat(actualResponse).isEqualTo(expectedResponse)
    }
}