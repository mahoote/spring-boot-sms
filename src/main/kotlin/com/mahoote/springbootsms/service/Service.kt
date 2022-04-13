package com.mahoote.springbootsms.service

import com.mahoote.springbootsms.models.SmsRequest
import com.mahoote.springbootsms.repos.SmsSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class Service (@Qualifier("twilio") @Autowired private val smsSender: SmsSender) {

    fun sendSms(smsRequest: SmsRequest) {
        smsSender.sendSms(smsRequest)
    }

    fun receiveSms(array: Array<String>) {
        smsSender.receiveSms(array)
    }

}