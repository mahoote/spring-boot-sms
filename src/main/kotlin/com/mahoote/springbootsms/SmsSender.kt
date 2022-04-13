package com.mahoote.springbootsms

import com.mahoote.springbootsms.models.SmsRequest

interface SmsSender {

    fun sendSms(smsRequest: SmsRequest)

}