package com.mahoote.springbootsms.repos

import com.mahoote.springbootsms.models.SmsRequest
import javax.servlet.http.HttpServletRequest

interface SmsSender {

    fun sendSms(smsRequest: SmsRequest)

    fun receiveSms(request: HttpServletRequest)

}