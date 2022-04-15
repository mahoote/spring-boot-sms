package com.mahoote.springbootsms.service

import com.mahoote.springbootsms.configuration.TwilioInitializer
import com.mahoote.springbootsms.models.SmsRequest
import com.mahoote.springbootsms.models.entities.UserEntity
import com.mahoote.springbootsms.repos.SmsReceiver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service("twilioReceiver")
class TwilioSmsReceiver(
    @Autowired private val questionService: QuestionService,
    @Autowired private val userService: UserService,
    @Autowired private val senderService: SenderService
    ): SmsReceiver {

    private val LOGGER: Logger = LoggerFactory.getLogger(TwilioInitializer::class.java)

    override fun receiveSms(requestMap: Map<String, String>) {
        val body: String? = requestMap.get("Body") // The text.
        val from: String? = requestMap.get("From") // The senders phone number.

        LOGGER.info("SMS received from {} with body: {}", from, body)
        reply(body, from)
    }

    private fun reply(body: String?, from: String?) {

        from?.let { number ->
            val text = body?.trim()

            var errorMessage = "I'm sorry, but im not sure what to do. Maybe you should try something else?"
            var smsRequest = SmsRequest(phoneNumber = number, message = errorMessage)

            with(text) {
                when {
                    equals("#REGISTER_ME") -> {
                        smsRequest = registerUser(number, smsRequest, errorMessage)
                    }
                    this?.startsWith("@ALL:") == true -> {
                        val keyWord = text?.substring(5)?.trim()
                        val question = questionService.getQuestionByKeyWord(keyWord)?.question
                        val users = userService.getUsers()

                        LOGGER.info(users.toString())

                        question?.let { q ->
                            for(user in users) {
                                smsRequest = SmsRequest(phoneNumber = user.phoneNumber, message = q)
                            }
                        }

                    }
                    else -> {
                        val question = questionService.getQuestionByKeyWord(text)?.question

                        question?.let { q ->
                            smsRequest = SmsRequest(phoneNumber = number, message = q)
                        }

                    }
                }
            }

            senderService.sendSms(smsRequest)
        }
    }

    private fun registerUser(
        number: String,
        smsRequest: SmsRequest,
        errorMessage: String
    ): SmsRequest {
        var smsRequest1 = smsRequest
        var errorMessage1 = errorMessage
        if (userService.getUserByPhoneNumber(number) == null) {
            userService.saveUser(UserEntity(phoneNumber = number))
            val confirmMessage = "You are now registered!"
            smsRequest1 = SmsRequest(phoneNumber = number, message = confirmMessage)
        } else {
            errorMessage1 = "You have already been registered!"
            smsRequest1 = SmsRequest(phoneNumber = number, message = errorMessage1)
        }
        return smsRequest1
    }

}