package com.embea.policyhandler.controllers

import com.embea.policyhandler.dtos.responses.CreatePolicyResponse
import com.embea.policyhandler.dtos.responses.UpdatePolicyResponse
import com.embea.policyhandler.helpers.TesDtosBuilder.dummyCreatePolicyRequest
import com.embea.policyhandler.helpers.TesDtosBuilder.dummyUpdatePolicyRequest
import com.embea.policyhandler.services.PolicyService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID


@ExtendWith(MockKExtension::class)
class PolicyControllerTest {

    @MockK
    private lateinit var policyService: PolicyService

    @InjectMockKs
    private lateinit var controller: PolicyController

    @Test
    fun `createPolicy should create a policy`() {
        val createPolicyRequest = dummyCreatePolicyRequest()

        val expectedResponse = CreatePolicyResponse(
            policyId = UUID.randomUUID(),
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

    @Test
    fun `updatePolicy should update a policy`() {
        val updatePolicyRequest = dummyUpdatePolicyRequest()

        val expectedResponse = UpdatePolicyResponse(
            policyId = UUID.randomUUID(),
            effectiveDate = LocalDate.now(),
            insuredPersons = emptyList(),
            totalPremium = BigDecimal.TEN
        )
        every { policyService.updatePolicy(updatePolicyRequest) } returns expectedResponse

        val actualResponse = controller.updatePolicy(
            updatePolicyRequest
        )

        assertThat(actualResponse).isEqualTo(expectedResponse)
    }
}