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

            var errorMessage = "Beklager, men jeg skjønner ikke hva du mener. Kanskje prøve noe annet?"
            var smsRequest = SmsRequest(phoneNumber = number, message = errorMessage)

            with(text) {
                when {
                    // Register new user.
                    equals("#REGISTERME", ignoreCase = true) -> {
                        smsRequest = registerUser(number, smsRequest, errorMessage)
                    }
                    equals("#DELETEME", ignoreCase = true) -> {
                        val user = userService.getUserByPhoneNumber(number)
                        user?.let {
                            userService.deleteUser(it)
                            smsRequest = SmsRequest(number, "Dette nummeret har blitt slettet fra vår database.")
                        }
                    }
                    // Message all users.
                    this?.startsWith("@ALL:") == true -> {
                        smsAll(text, smsRequest)
                    }
                    equals("@STATUS") -> {
                        smsRequest = statusMessage(number)
                    }
                    // Check the database for keyWords.
                    else -> {
                        val question = questionService.getQuestionByKeyWord(text?.trim())?.question

                        question?.let { q ->
                            smsRequest = SmsRequest(phoneNumber = number, message = q)
                        }

                    }
                }
            }

            senderService.sendSms(smsRequest)
        }
    }

    private fun statusMessage(number: String): SmsRequest {
        val userSize = userService.getUsers().size
        val questionSize = questionService.getAllQuestions().size

        val statusMessage =
            "STATUS: 200 ok\n" +
            "Brukere: $userSize\n" +
            "Spørsmål: $questionSize"

        return SmsRequest(phoneNumber = number, message = statusMessage)
    }

    private fun smsAll(text: String?, smsRequest: SmsRequest) {
        var smsRequest1 = smsRequest
        val keyWord = text?.substring(5)?.trim()
        val question = questionService.getQuestionByKeyWord(keyWord)?.question
        val users = userService.getUsers()

        question?.let { q ->
            for (user in users) {
                smsRequest1 = SmsRequest(phoneNumber = user.phoneNumber, message = q)
                senderService.sendSms(smsRequest1)
            }
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
            val confirmMessage = "Du er nå registrert!"
            smsRequest1 = SmsRequest(phoneNumber = number, message = confirmMessage)
        } else {
            errorMessage1 = "Du er allerede registrert."
            smsRequest1 = SmsRequest(phoneNumber = number, message = errorMessage1)
        }
        return smsRequest1
    }

}