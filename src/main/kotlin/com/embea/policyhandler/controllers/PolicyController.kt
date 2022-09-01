package com.embea.policyhandler.controllers

import com.embea.policyhandler.dtos.exceptions.InvalidStartDateException
import com.embea.policyhandler.dtos.requests.CreatePolicyRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller("/")
class PolicyController {

    @PostMapping("/policy")
    fun createPolicy(@RequestBody createPolicyRequest: CreatePolicyRequest): Any {
        throw InvalidStartDateException()
    }
}