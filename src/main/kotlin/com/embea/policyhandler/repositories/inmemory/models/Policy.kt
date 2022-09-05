package com.embea.policyhandler.repositories.inmemory.models

import java.time.LocalDate
import java.util.UUID

data class Policy(
    val id: UUID = UUID.randomUUID(),
    val startDate: LocalDate,
    val effectiveDate: LocalDate? = null,
    val requestDate: LocalDate? = null,
    val insuredPersons: Set<InsuredPerson>
)