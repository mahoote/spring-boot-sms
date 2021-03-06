package com.mahoote.springbootsms.service

import com.mahoote.springbootsms.models.SmsRequest
import com.mahoote.springbootsms.repos.SmsSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class SenderService (@Qualifier("twilioSender") @Autowired private val smsSender: SmsSender) {

    fun sendSms(smsRequest: SmsRequest): ResponseEntity<SmsRequest> {
        return smsSender.sendSms(smsRequest)
    }

}