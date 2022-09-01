package com.embea.policyhandler.dtos

import java.math.BigDecimal

data class InsuredPersons(
    private val firstName: String,
    private val secondName: String,
    private val premium: BigDecimal
)