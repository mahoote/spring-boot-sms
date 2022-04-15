package com.mahoote.springbootsms.unittests

import com.mahoote.springbootsms.models.entities.UserEntity
import com.mahoote.springbootsms.repos.UserRepo
import com.mahoote.springbootsms.service.UserService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.Assert.assertFalse

class UserServiceUnitTests {

    private val userRepo = mockk<UserRepo>()
    private val userService = UserService(userRepo)

    @Test
    fun shouldGetAllUsers() {

        val user1 = UserEntity(0, "+4795040924")
        val user2 = UserEntity(1, "+4798765432")
        val user3 = UserEntity(2, "+4712345678")

        every { userRepo.findAll() } answers {
            mutableListOf(user1, user2, user3)
        }

        val users = userService.getUsers()
        assert(users.size == 3)
        assert(users.first { it.id == 0.toLong()}.phoneNumber == "+4795040924")

    }

    @Test
    fun shouldGetValidUserByPhoneNumber() {
        val userId: Long = 0
        val validNumber = "+4795040924"

        val user1 = UserEntity(userId, validNumber)

        every { userRepo.findByPhoneNumber(any()) } answers {
            if(valueAny == validNumber) user1 else null
        }

        val user = userService.getUserByPhoneNumber(validNumber)
        if (user != null) {
            assert(user.id == userId)
            assert(user.phoneNumber == validNumber)
        }
    }

    @Test
    fun shouldNotGetInvalidUserByPhoneNumber() {

        val validUserId: Long = 0
        val validNumber = "+4795040924"

        val invalidNumber1 = "4795040924"
        val invalidNumber2 = "+95040924"
        val invalidNumber3 = "+479504092"

        val validUser = UserEntity(validUserId, validNumber)

        every { userRepo.findByPhoneNumber(any()) } answers {
            if(valueAny == validNumber) validUser else null
        }

        val invalidUser1 = userService.getUserByPhoneNumber(invalidNumber1)
        val invalidUser2 = userService.getUserByPhoneNumber(invalidNumber2)
        val invalidUser3 = userService.getUserByPhoneNumber(invalidNumber3)

        assertFalse(invalidUser1?.phoneNumber == validNumber)
        assertFalse(invalidUser2?.phoneNumber == validNumber)
        assertFalse(invalidUser3?.phoneNumber == validNumber)

    }

}