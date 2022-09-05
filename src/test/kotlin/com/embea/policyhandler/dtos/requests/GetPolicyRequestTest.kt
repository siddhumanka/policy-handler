package com.embea.policyhandler.dtos.requests

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

class GetPolicyRequestTest {

    @Test
    fun `should return current date if request date is null`() {
        val actualRequest = GetPolicyRequest.from(UUID.randomUUID(), null)

        assertThat(actualRequest.requestDate).isNotNull
    }

    @Test
    fun `should return parsed date if request date is not null`() {
        val actualRequest = GetPolicyRequest.from(UUID.randomUUID(), "12.04.2021")

        assertThat(actualRequest.requestDate).isEqualTo(
            LocalDate.parse(
                "12.04.2021",
                DateTimeFormatter.ofPattern("dd.MM.yyyy")
            )
        )
    }
}