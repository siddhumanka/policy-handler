package com.embea.policyhandler.controllers

import com.embea.policyhandler.dtos.requests.CreatePolicyRequest
import com.embea.policyhandler.dtos.requests.UpdatePolicyRequest
import com.embea.policyhandler.dtos.responses.CreatePolicyResponse
import com.embea.policyhandler.dtos.responses.UpdatePolicyResponse
import com.embea.policyhandler.services.PolicyService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController("/")
class PolicyController(private val policyService: PolicyService) {

    @PostMapping("/policy")
    fun createPolicy(@Valid @RequestBody createPolicyRequest: CreatePolicyRequest): CreatePolicyResponse {
        return policyService.createPolicy(createPolicyRequest)
    }

    @PutMapping("/policy")
    fun updatePolicy(@RequestBody updatePolicyRequest: UpdatePolicyRequest): UpdatePolicyResponse {
        return policyService.updatePolicy(updatePolicyRequest)
    }


}