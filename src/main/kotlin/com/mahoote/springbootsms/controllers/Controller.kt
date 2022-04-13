package com.mahoote.springbootsms.controllers

import com.mahoote.springbootsms.models.SmsRequest
import com.mahoote.springbootsms.service.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/sms")
class Controller(@Autowired private val service: Service) {

    @PostMapping
    fun sendSms(@Validated @RequestBody smsRequest: SmsRequest) {
        service.sendSms(smsRequest)
    }

}