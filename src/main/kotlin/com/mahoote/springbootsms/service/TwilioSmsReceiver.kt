package com.mahoote.springbootsms.service

import com.mahoote.springbootsms.configuration.TwilioInitializer
import com.mahoote.springbootsms.repos.SmsReceiver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service("twilioReceiver")
class TwilioSmsReceiver: SmsReceiver {

    private val LOGGER: Logger = LoggerFactory.getLogger(TwilioInitializer::class.java)

    override fun receiveSms(requestMap: Map<String, String>) {
        LOGGER.info("Receive sms {}", requestMap)
    }

}