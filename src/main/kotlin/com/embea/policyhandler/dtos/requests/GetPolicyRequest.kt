package com.embea.policyhandler.dtos.requests

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.validation.annotation.Validated
import java.time.LocalDate
import java.util.UUID

@Validated
data class GetPolicyRequest(
    val policyId: UUID,
    @JsonFormat(pattern = "dd.MM.yyyy")
    val requestDate: LocalDate
) {
    companion object {
        fun from(policyId: UUID, requestDate: LocalDate?): GetPolicyRequest {
            return GetPolicyRequest(policyId, requestDate ?: LocalDate.now())
        }
    }
}
