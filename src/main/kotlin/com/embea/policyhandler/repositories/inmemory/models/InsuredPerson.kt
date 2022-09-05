package com.embea.policyhandler.repositories.inmemory.models

import com.embea.policyhandler.dtos.InsuredPersonDto
import java.math.BigDecimal

data class InsuredPerson(
    val id: Long? = null,
    val firstName: String,
    val secondName: String,
    val premium: BigDecimal
) {
    fun toDto() = InsuredPersonDto(id = id, firstName = firstName, secondName = secondName, premium = premium)
}