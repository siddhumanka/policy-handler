package com.embea.policyhandler.dtos.requests

import com.embea.policyhandler.dtos.InsuredPersonDto
import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.validation.annotation.Validated
import java.time.LocalDate
import java.util.UUID
import javax.validation.constraints.Future

@Validated
data class UpdatePolicyRequest(
    val policyId: UUID,
    @field:Future(message = "effectiveDate should be in future always")
    @JsonFormat(pattern = "dd.MM.yyyy")
    val effectiveDate: LocalDate,
    val insuredPersons: Set<InsuredPersonDto>
)
