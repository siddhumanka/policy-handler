package com.embea.policyhandler.integrationtests

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.net.URI

@SpringBootTest
@AutoConfigureMockMvc
class PolicyIntegrationTest(@Autowired private val mockMvc: MockMvc) {

    @Test
    fun `POST for invalid-url should return 404`() {

        mockMvc.post(URI("/invalid-url")) {
            contentType = APPLICATION_JSON
        }.andExpect { status { isNotFound() } }

    }

    @Test
    fun `POST policy should return 400 if date provided is in past`() {

        mockMvc.post(URI("/policy")) {
            contentType = APPLICATION_JSON
            content = """{
                            "startDate": "15.07.2022",
                            "insuredPersons": [
                                {
                                    "firstName": "Jane",
                                    "secondName": "Johnson",
                                    "premium": 12.90
                                },
                                {
                                    "firstName": "Jack",
                                    "secondName": "Doe",
                                    "premium": 15.90
                                }
                            ]
                        }""".trimMargin()
        }.andExpect { status { isBadRequest() } }
    }

    @Test
    fun `POST policy should create a policy`() {

        val startDate = "15.07.2023"
        mockMvc.post(URI("/policy")) {
            contentType = APPLICATION_JSON
            content = """{
                            "startDate": "$startDate",
                            "insuredPersons": [
                                {
                                    "firstName": "Jane",
                                    "secondName": "Johnson",
                                    "premium": 12.90
                                },
                                {
                                    "firstName": "Jack",
                                    "secondName": "Doe",
                                    "premium": 15.90
                                }
                            ]
                        }""".trimMargin()
        }.andExpect { status { isOk() } }
            .andExpect { MockMvcResultMatchers.jsonPath("$.policyId").exists() }
            .andExpect { MockMvcResultMatchers.jsonPath("$.startDate").value(startDate) }
            .andExpect { MockMvcResultMatchers.jsonPath("$.insuredPersons").isNotEmpty }
            .andExpect { MockMvcResultMatchers.jsonPath("$.insuredPersons[0].id").isNotEmpty }
            .andExpect { MockMvcResultMatchers.jsonPath("$.totalPremium").value(28.80) }
    }
}
