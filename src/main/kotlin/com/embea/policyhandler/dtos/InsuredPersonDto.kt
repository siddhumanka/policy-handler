package com.embea.policyhandler.dtos

import com.embea.policyhandler.models.InsuredPerson
import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal

@JsonInclude(JsonInclude.Include.NON_NULL)
data class InsuredPersonDto(
    val id: Long? = null,
    val firstName: String,
    val secondName: String,
    val premium: BigDecimal
) {
    fun toInsuredPerson(id: Long) =
        InsuredPerson(id = id, firstName = firstName, secondName = secondName, premium = premium)
}