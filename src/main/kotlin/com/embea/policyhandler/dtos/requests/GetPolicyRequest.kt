package com.embea.policyhandler.dtos.requests

import org.springframework.validation.annotation.Validated
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

@Validated
data class GetPolicyRequest(
    val policyId: UUID,
    val requestDate: LocalDate
) {
    companion object {
        fun from(policyId: UUID, requestDateString: String?): GetPolicyRequest {
            val requestDate =  if (requestDateString.isNullOrBlank()){
                LocalDate.now()
            } else{
                LocalDate.parse(requestDateString, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            }

            return GetPolicyRequest(policyId, requestDate)
        }
    }
}
