package com.github.ricardobaumann

import com.fasterxml.jackson.core.type.TypeReference
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.auth.*
import com.fasterxml.jackson.databind.*
import io.ktor.jackson.*
import kotlin.test.*
import io.ktor.server.testing.*
import org.mockito.Mockito

class ApplicationTest {
    private val addressController = Mockito.mock(AddressController::class.java)
    private val objectMapper = ObjectMapper()

    @Test
    fun testRoot() {
        withTestApplication({ module(addressController = addressController) }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("HELLO WORLD!", response.content)
            }
        }
    }

    @Test
    fun testGetAddresses() {
        val expectedResult = listOf(
            Address(
                id = "1",
                firstName = "first",
                lastName = "last",
                street = "street",
                country = "country",
                zip = "zip"
            )
        )

        Mockito.`when`(addressController.getAll()).thenReturn(expectedResult)

        withTestApplication({ module(addressController = addressController) }) {
            handleRequest(HttpMethod.Get, "/addresses").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(expectedResult, objectMapper.readValue(response.content, object : TypeReference<List<Address>>() {}))
            }
        }
    }
}
