package com.mahoote.springbootsms.repos

import com.mahoote.springbootsms.models.SmsRequest

interface SmsSender {

    fun sendSms(smsRequest: SmsRequest)

    fun receiveSms(smsRequest: SmsRequest)

}