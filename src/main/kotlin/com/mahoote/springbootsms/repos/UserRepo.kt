package com.mahoote.springbootsms.repos

import com.mahoote.springbootsms.models.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepo: JpaRepository<UserEntity, Long> {

    fun findByPhoneNumber(phoneNumber: String): UserEntity?

}