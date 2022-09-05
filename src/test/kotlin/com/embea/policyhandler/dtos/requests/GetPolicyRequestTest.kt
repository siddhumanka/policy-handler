package com.embea.policyhandler.dtos.requests

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.UUID

class GetPolicyRequestTest {

    @Test
    fun `should return current date if request date is null`() {
        val actualRequest = GetPolicyRequest.from(UUID.randomUUID(), null)

        assertThat(actualRequest.requestDate).isNotNull
    }
}