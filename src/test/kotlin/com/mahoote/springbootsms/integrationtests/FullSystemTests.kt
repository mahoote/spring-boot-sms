package com.mahoote.springbootsms.integrationtests

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@ActiveProfiles("tests")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
class FullSystemTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun shouldAddNewUserToDatabaseWithApiCall() {
        val newUser = mockMvc.post("/api/v1/sms/send") {
            contentType = APPLICATION_JSON
            content = "{\n" +
                    "    \"phoneNumber\": \"+4795040924\",\n" +
                    "    \"message\": \"Test\"\n" +
                    "}"
        }
            .andExpect { status { isOk() } }
            .andReturn()


    }

}