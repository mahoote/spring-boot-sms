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

            val errorMessage = "I'm sorry, but im not sure what to do. Maybe you should try something else?"
            var smsRequest = SmsRequest(phoneNumber = number, message = errorMessage)

            if(text == "001_create_user") {
                if(userService.getUserByPhoneNumber(number) == null) {
                    userService.saveUser(UserEntity(phoneNumber = number))
                    val confirmMessage = "User registered!"
                    smsRequest = SmsRequest(phoneNumber = number, message = confirmMessage)
                }
            } else {
                val question = questionService.getQuestionByKeyWord(text)?.question

                question?.let { q ->
                    smsRequest = SmsRequest(phoneNumber = number, message = q)
                    senderService.sendSms(smsRequest)
                }

            }

            senderService.sendSms(smsRequest)
        }
    }

}