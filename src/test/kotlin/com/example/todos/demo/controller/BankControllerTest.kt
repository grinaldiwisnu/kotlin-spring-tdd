package com.example.todos.demo.controller

import com.example.todos.demo.model.Bank
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {


    val baseUrl = "/api/banks"

    @Nested
    @DisplayName("GET /api/banks/")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {
        @Test
        private fun `should return all banks`() {
            // given
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status().isOk
                    content { contentType(MediaType.APPLICATION_JSON) }
                }
        }

    }

    @Nested
    @DisplayName("GET /api/banks/{acocuntNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank {
        @Test
        fun `should return bank with the given number`() {
            val accountNumber = "1234"
            // given
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status().isOk
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust") { value("3.14") }
                    jsonPath("$.transactionFee") { value("17") }
                }
        }

        @Test
        fun `should return NOT FOUND if the account number does not exist`() {
            val accountNumber = "1234"
            // given
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status().isNotFound
                }
        }
    }

    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewBank {
        @Test
        fun `should add the new bank`() {
            // given
            val newBank = Bank("ac12345", 31.4, 2)

            // when
            val result = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }

            result.andDo { print() }
                .andExpect {
                    status().isCreated
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.accountNumber") { value("ac12345") }
                    jsonPath("$.trust") { value("31.4") }
                    jsonPath("$.transactionFee") { value("2") }

                }
        }

        @Test
        fun `should return BAD REQUEST if account number exist`() {
            // given
            val newBank = Bank("1234", 1.0, 2)

            // when
            val result = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }

            result.andDo { print() }
                .andExpect {
                    status().isBadRequest
                }

        }
    }

    @Nested
    @DisplayName("PATCH /api/banks/")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchBank {
        @Test
        fun `should update the field of bank`() {
            // given
            val existBank = Bank("1234", 10.0, 72)

            // when
            val result = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(existBank)
            }

            // then
            result.andDo { print() }
                .andExpect {
                    status().isOk
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.accountNumber") { value("1234") }
                    jsonPath("$.trust") { value("10.0") }
                    jsonPath("$.transactionFee") { value("72") }
                }
        }

        @Test
        fun `should return BAD REQUEST if accountNumber not found`() {
            // given
            val bank = Bank("01293", 0.0, 0)

            // when
            val result = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(bank)
            }

            // then
            result.andDo { print() }
                .andExpect {
                    status().isBadRequest
                }
        }
    }

    @Nested
    @DisplayName("DELETE /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteBank {
        @Test
        fun `should return accountNumber that already deleted`() {
            // given
            val accountNumber = "1234"

            // when
            val result = mockMvc.delete("$baseUrl/$accountNumber") {
                contentType = MediaType.APPLICATION_JSON
            }

            // then
            result.andDo { print() }
                .andExpect {
                    status().isOk
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.accountNumber") { value("1234") }
                }
        }

        @Test
        fun `should return NOT FOUND if accountNumber does not exist`() {
            // given
            val accountNumber = "does_not_exist"

            // when
            val result = mockMvc.delete("$baseUrl/$accountNumber")

            // then
            result.andDo { print() }
                .andExpect {
                    status().isNotFound
                }
        }
    }
}