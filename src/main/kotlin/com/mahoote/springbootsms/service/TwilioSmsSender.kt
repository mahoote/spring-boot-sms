package com.mahoote.springbootsms.service

import com.mahoote.springbootsms.repos.SmsSender
import com.mahoote.springbootsms.configuration.TwilioConfig
import com.mahoote.springbootsms.configuration.TwilioInitializer
import com.mahoote.springbootsms.models.SmsRequest
import com.mahoote.springbootsms.validation.PhoneNumberValidation.validatePhoneNumber
import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@Service("twilioSender")
class TwilioSmsSender(
    @Autowired private var twilioConfig: TwilioConfig
): SmsSender {

    private val LOGGER: Logger = LoggerFactory.getLogger(TwilioInitializer::class.java)

    override fun sendSms(smsRequest: SmsRequest): ResponseEntity<SmsRequest> {
        if(isPhoneNumberValid(smsRequest.phoneNumber)) {
            val to = PhoneNumber(smsRequest.phoneNumber)
            val from = PhoneNumber(twilioConfig.phoneNumber)
            val message = smsRequest.message
            val creator = Message.creator(to, from, message)
            creator.create()

            LOGGER.info("Send sms {}", smsRequest)

            val uri =
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/sms/send").toUriString())
            return ResponseEntity.created(uri).body(smsRequest)
        } else {
            throw IllegalArgumentException("Phone number [${smsRequest.phoneNumber}] is not valid.")
        }
    }

    private fun isPhoneNumberValid(phoneNumber: String): Boolean {
        return validatePhoneNumber(phoneNumber)
    }

}