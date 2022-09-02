package com.embea.policyhandler.dtos

import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal

@JsonInclude(JsonInclude.Include.NON_NULL)
data class InsuredPersonsDto(
    val id: Int? = null,
    val firstName: String,
    val secondName: String,
    val premium: BigDecimal
)