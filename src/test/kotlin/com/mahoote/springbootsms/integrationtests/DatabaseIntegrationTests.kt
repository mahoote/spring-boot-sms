package com.mahoote.springbootsms.integrationtests

import com.mahoote.springbootsms.models.entities.UserEntity
import com.mahoote.springbootsms.service.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("tests")
@Import(UserService::class)
class DatabaseIntegrationTests(@Autowired private val userService: UserService) {

    @Test
    fun shouldGetAllUsers() {
        val result = userService.getUsers()
        assert(result.size == 2)
        assert(result.first { it.id == 1.toLong()}.phoneNumber == "+4795040924")
    }

    @Test
    fun shouldAddUserToDatabase() {
        val phoneNumber = "+4785963215"

        val user = UserEntity(phoneNumber = phoneNumber)
        userService.saveUser(user)

        val retrievedUser = userService.getUserByPhoneNumber(phoneNumber)
        assert(retrievedUser != null)
        assert(retrievedUser?.phoneNumber == phoneNumber)
    }

}