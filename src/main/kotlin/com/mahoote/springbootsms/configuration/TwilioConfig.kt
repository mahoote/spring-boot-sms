package com.mahoote.springbootsms.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "twilio")
data class TwilioConfig (
    var accountSid: String = "",
    var authToken: String = "",
    var trialNumber: String = ""
)