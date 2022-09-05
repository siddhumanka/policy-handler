package com.embea.policyhandler.controllers

import com.embea.policyhandler.dtos.requests.CreatePolicyRequest
import com.embea.policyhandler.dtos.requests.GetPolicyRequest
import com.embea.policyhandler.dtos.requests.UpdatePolicyRequest
import com.embea.policyhandler.dtos.responses.CreatePolicyResponse
import com.embea.policyhandler.dtos.responses.GetPolicyResponse
import com.embea.policyhandler.dtos.responses.UpdatePolicyResponse
import com.embea.policyhandler.services.PolicyService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID
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

    @GetMapping("/policy")
    fun getPolicy(
        @RequestParam("policyId") policyId: String,
        @RequestParam("requestDate") requestDate: String?
    ): GetPolicyResponse {
        val parsedRequestDate = LocalDate.parse(requestDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        val parsedPolicyId = UUID.fromString(policyId)
        return policyService.getPolicy(GetPolicyRequest.from(parsedPolicyId, parsedRequestDate))
    }


}