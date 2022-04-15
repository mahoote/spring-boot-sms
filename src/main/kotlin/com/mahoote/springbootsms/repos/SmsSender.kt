package com.mahoote.springbootsms.repos

import com.mahoote.springbootsms.models.SmsRequest
import org.springframework.http.ResponseEntity

interface SmsSender {

    fun sendSms(smsRequest: SmsRequest): ResponseEntity<SmsRequest>

}