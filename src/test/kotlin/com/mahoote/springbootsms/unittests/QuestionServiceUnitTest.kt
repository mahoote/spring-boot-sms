package com.mahoote.springbootsms.unittests

import com.mahoote.springbootsms.models.entities.QuestionEntity
import com.mahoote.springbootsms.repos.QuestionRepo
import com.mahoote.springbootsms.service.QuestionService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class QuestionServiceUnitTest {

    private val questionRepo = mockk<QuestionRepo>()
    private val questionService = QuestionService(questionRepo)

    @Test
    fun shouldGetQuestionByKeyWord() {
        val q = "Is it working?"
        val keyWord = "Work"

        val exQuestion = QuestionEntity(id = 1, question = q, keyWord = keyWord)

        every { questionRepo.findByKeyWord(keyWord) } answers {
            exQuestion
        }

        val question = questionService.getQuestionByKeyWord(keyWord)
        assert(question == exQuestion)
    }

    @Test
    fun shouldFailToGetQuestionByKeyWord() {
        val wrongKeyWord = "Don't Work"

        every { questionRepo.findByKeyWord(wrongKeyWord) } answers {
            null
        }

        val question = questionService.getQuestionByKeyWord(wrongKeyWord)
        assert(question == null)
    }

}