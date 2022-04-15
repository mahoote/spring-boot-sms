package com.mahoote.springbootsms.service

import com.mahoote.springbootsms.models.entities.UserEntity
import com.mahoote.springbootsms.repos.UserRepo
import com.mahoote.springbootsms.validation.PhoneNumberValidation.validatePhoneNumber
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(@Autowired private val userRepo: UserRepo) {

    fun getUsers(): MutableList<UserEntity> {
        return userRepo.findAll()
    }

    fun getUserByPhoneNumber(phoneNumber: String): UserEntity? {
        val user = userRepo.findByPhoneNumber(phoneNumber)
        return validateUser(user)
    }

    private fun validateUser(user: UserEntity?): UserEntity? {
        if(user != null && validatePhoneNumber(user.phoneNumber))
            return user
        return null
    }

}