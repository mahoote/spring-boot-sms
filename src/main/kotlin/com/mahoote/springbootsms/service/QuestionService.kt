package com.mahoote.springbootsms.service

import com.mahoote.springbootsms.models.entities.QuestionEntity
import com.mahoote.springbootsms.repos.QuestionRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class QuestionService(@Autowired private val questionRepo: QuestionRepo) {

    fun getQuestionByKeyWord(body: String?): QuestionEntity? {
        return questionRepo.findByKeyWord(body)
    }

    fun getAllQuestions(): MutableList<QuestionEntity> {
        return questionRepo.findAll()
    }

}