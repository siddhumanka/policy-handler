package com.embea.policyhandler.integrationtests

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import org.springframework.transaction.annotation.Transactional
import java.net.URI


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PolicyIntegrationTest(@Autowired private val mockMvc: MockMvc) {

    private val mapper = jacksonObjectMapper()

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

        val createPolicyResponse = createPolicy(startDate)

        val jsonNode = mapper.readTree(createPolicyResponse.response.contentAsString)
        assertThat(jsonNode.path("policyId")).isNotNull
        assertThat(jsonNode.path("startDate").asText()).isEqualTo(startDate)
        assertThat(jsonNode.path("insuredPersons").get(0).path("id").asInt()).isEqualTo(1)
        assertThat(jsonNode.path("insuredPersons").get(0).path("firstName").asText()).isEqualTo("Jane")
        assertThat(jsonNode.path("insuredPersons").get(0).path("secondName").asText()).isEqualTo("Johnson")
        assertThat(jsonNode.path("insuredPersons").get(0).path("premium").asDouble()).isEqualTo(12.90)
        assertThat(jsonNode.path("insuredPersons").get(1).path("id").asInt()).isEqualTo(2)
        assertThat(jsonNode.path("insuredPersons").get(1).path("firstName").asText()).isEqualTo("Jack")
        assertThat(jsonNode.path("insuredPersons").get(1).path("secondName").asText()).isEqualTo("Doe")
        assertThat(jsonNode.path("insuredPersons").get(1).path("premium").asDouble()).isEqualTo(15.90)
        assertThat(jsonNode.path("totalPremium").asDouble()).isEqualTo(28.80)
    }

    @Test
    fun `PUT policy should update a policy`() {
        val effectiveDate = "15.08.2023"
        val createPolicyResponse = createPolicy()
        val createPolicyJsonNode = mapper.readTree(createPolicyResponse.response.contentAsString)
        val policyId = createPolicyJsonNode.path("policyId").asText()

        val updatePolicyResponse = mockMvc.put(URI("/policy")) {
            contentType = APPLICATION_JSON
            content = """{
                                "policyId": "$policyId",
                                "effectiveDate": "$effectiveDate",
                                "insuredPersons": [
                                    {
                                        "id": 1,
                                        "firstName": "Jane",
                                        "secondName": "Johnson",
                                        "premium": 12.90
                                    },
                                    {
                                        "firstName": "Will",
                                        "secondName": "SlapSmith",
                                        "premium": 12.90
                                    }
                                ]
                            }""".trimMargin()
        }.andExpect { status { isOk() } }.andReturn()

        val jsonNode = mapper.readTree(updatePolicyResponse.response.contentAsString)
        assertThat(jsonNode.path("policyId").asText()).isEqualTo(policyId)
        assertThat(jsonNode.path("effectiveDate").asText()).isEqualTo(effectiveDate)
        assertThat(jsonNode.path("insuredPersons").get(0).path("id").asInt()).isEqualTo(1)
        assertThat(jsonNode.path("insuredPersons").get(0).path("firstName").asText()).isEqualTo("Jane")
        assertThat(jsonNode.path("insuredPersons").get(0).path("secondName").asText()).isEqualTo("Johnson")
        assertThat(jsonNode.path("insuredPersons").get(0).path("premium").asDouble()).isEqualTo(12.90)
        assertThat(jsonNode.path("insuredPersons").get(1).path("id").asInt()).isEqualTo(3)
        assertThat(jsonNode.path("insuredPersons").get(1).path("firstName").asText()).isEqualTo("Will")
        assertThat(jsonNode.path("insuredPersons").get(1).path("secondName").asText()).isEqualTo("SlapSmith")
        assertThat(jsonNode.path("insuredPersons").get(1).path("premium").asDouble()).isEqualTo(12.90)
        assertThat(jsonNode.path("totalPremium").asDouble()).isEqualTo(25.80)
    }

    @Test
    fun `GET policy should return policy for a given request date`() {
        val requestDate = "17.07.2023"
        val createPolicyResponse = createPolicy()
        val createPolicyJsonNode = mapper.readTree(createPolicyResponse.response.contentAsString)
        val policyId = createPolicyJsonNode.path("policyId").asText()

        val updatePolicyResponse = mockMvc.get(URI("/policy?policyId=$policyId&requestDate=$requestDate")) {
            contentType = APPLICATION_JSON
        }.andExpect { status { isOk() } }.andReturn()

        val jsonNode = mapper.readTree(updatePolicyResponse.response.contentAsString)
        assertThat(jsonNode.path("policyId").asText()).isEqualTo(policyId)
        assertThat(jsonNode.path("effectiveDate").asText()).isEqualTo(requestDate)
        assertThat(jsonNode.path("insuredPersons").get(0).path("id").asInt()).isEqualTo(1)
        assertThat(jsonNode.path("insuredPersons").get(0).path("firstName").asText()).isEqualTo("Jane")
        assertThat(jsonNode.path("insuredPersons").get(0).path("secondName").asText()).isEqualTo("Johnson")
        assertThat(jsonNode.path("insuredPersons").get(0).path("premium").asDouble()).isEqualTo(12.90)
        assertThat(jsonNode.path("insuredPersons").get(1).path("id").asInt()).isEqualTo(3)
        assertThat(jsonNode.path("insuredPersons").get(1).path("firstName").asText()).isEqualTo("Will")
        assertThat(jsonNode.path("insuredPersons").get(1).path("secondName").asText()).isEqualTo("SlapSmith")
        assertThat(jsonNode.path("insuredPersons").get(1).path("premium").asDouble()).isEqualTo(12.90)
        assertThat(jsonNode.path("totalPremium").asDouble()).isEqualTo(25.80)
    }

    private fun createPolicy(startDate: String = "15.07.2023") = mockMvc.post(URI("/policy")) {
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
    }.andExpect { status { isOk() } }.andReturn()
}
