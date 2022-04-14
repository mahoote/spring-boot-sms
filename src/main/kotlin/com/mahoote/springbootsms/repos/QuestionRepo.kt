package com.mahoote.springbootsms.repos

import com.mahoote.springbootsms.models.entities.QuestionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionRepo: JpaRepository<QuestionEntity, Long> {

    fun findByKeyWord(keyWord: String?): QuestionEntity

}