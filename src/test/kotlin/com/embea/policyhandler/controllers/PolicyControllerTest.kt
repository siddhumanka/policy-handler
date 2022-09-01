package com.embea.policyhandler.controllers

import com.embea.policyhandler.dtos.exceptions.InvalidStartDateException
import com.embea.policyhandler.dtos.requests.CreatePolicyRequest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate


class PolicyControllerTest {
    private val controller = PolicyController()

    @Test
    fun `should throw InvalidStartDateException if start date provided is in past`() {
        val startDate = LocalDate.of(1988, 5, 5)

        assertThrows<InvalidStartDateException> {  controller.createPolicy(
            CreatePolicyRequest(
                startDate = startDate,
                insuredPersons = emptyList()
            )
        ) }
    }
}