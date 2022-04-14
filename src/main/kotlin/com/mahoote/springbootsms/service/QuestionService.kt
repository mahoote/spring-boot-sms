package com.mahoote.springbootsms.service

import com.mahoote.springbootsms.repos.QuestionRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class QuestionService(@Autowired private val questionRepo: QuestionRepo) {

    fun reply(body: String?): String? {
        val question = questionRepo.findByKeyWord(body)
        return question?.question
    }

}