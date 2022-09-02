package com.embea.policyhandler.controllers

import com.embea.policyhandler.dtos.exceptions.InvalidStartDateException
import com.embea.policyhandler.dtos.requests.CreatePolicyRequest
import com.embea.policyhandler.dtos.responses.CreatePolicyResponse
import com.embea.policyhandler.services.PolicyService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController("/")
class PolicyController(private val policyService: PolicyService) {

    @PostMapping("/policy")
    fun createPolicy(@RequestBody createPolicyRequest: CreatePolicyRequest): CreatePolicyResponse {
        if (createPolicyRequest.startDate.isBefore(LocalDate.now()))
            throw InvalidStartDateException()
        else
            return policyService.createPolicy(createPolicyRequest)
    }
}