package com.embea.policyhandler

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
class PolicyHandlerApplication

fun main(args: Array<String>) {
	runApplication<PolicyHandlerApplication>(*args)
}
