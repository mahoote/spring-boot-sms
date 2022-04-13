package com.mahoote.springbootsms.configuration

import com.twilio.Twilio
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration

@Configuration
class TwilioInitializer (@Autowired private val twilioConfig: TwilioConfig) {

    private val LOGGER: Logger = LoggerFactory.getLogger(TwilioInitializer::class.java)

    init {
        Twilio.init(twilioConfig.accountSid, twilioConfig.authToken)
        LOGGER.info("Twilio initialized ... with account sid {}", twilioConfig.accountSid)
    }

}