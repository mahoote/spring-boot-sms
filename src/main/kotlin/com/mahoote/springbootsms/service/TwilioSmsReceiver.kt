package com.mahoote.springbootsms.service

import com.mahoote.springbootsms.configuration.TwilioInitializer
import com.mahoote.springbootsms.models.SmsRequest
import com.mahoote.springbootsms.repos.SmsReceiver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service("twilioReceiver")
class TwilioSmsReceiver(
    @Autowired private val questionService: QuestionService,
    @Autowired private val smsSender: TwilioSmsSender
    ): SmsReceiver {

    private val LOGGER: Logger = LoggerFactory.getLogger(TwilioInitializer::class.java)

    override fun receiveSms(requestMap: Map<String, String>) {
        val body: String? = requestMap.get("Body") // The text.
        val from: String? = requestMap.get("From") // The senders phone number.

        LOGGER.info("SMS received from {} with body: {}", from, body)
        reply(body, from)
    }

    private fun reply(body: String?, from: String?) {
        val question = questionService.reply(body)
        if(question != null && from != null) {
            val smsRequest = SmsRequest(phoneNumber = from, message = question)
            smsSender.sendSms(smsRequest)
        }
    }

}