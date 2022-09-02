package com.embea.policyhandler.models

import com.embea.policyhandler.dtos.InsuredPersonDto
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "INSURED_PERSON")
data class InsuredPerson(
    @Id
    val id: Long,
    val firstName: String,
    val secondName: String,
    val premium: BigDecimal
) {
    fun toDto() = InsuredPersonDto(id = id, firstName = firstName, secondName = secondName, premium = premium)

    override fun equals(other: Any?) = when {
        this === other -> true
        javaClass != other?.javaClass -> false
        id != (other as InsuredPerson).id -> false
        else -> false
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}