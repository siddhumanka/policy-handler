package com.embea.policyhandler.models

import java.time.LocalDate
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "POLICY")
data class Policy(
    @Id
    val id: UUID = UUID.randomUUID(),
    val startDate: LocalDate,
    val effectiveDate: LocalDate? = null,

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "policy_id", referencedColumnName = "id", nullable = false)
    val insuredPersons: MutableList<InsuredPerson>
)