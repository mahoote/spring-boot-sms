package com.mahoote.springbootsms.controllers

import com.mahoote.springbootsms.models.SmsRequest
import com.mahoote.springbootsms.service.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1")
class Controller(@Autowired private val service: Service) {

    @GetMapping
    fun testGet(): String {
        return "It worked!"
    }

    @PostMapping("/receive")
    fun receiveSms(@RequestBody map: Map<String, String>) {
        service.receiveSms(map)
    }


    @PostMapping("/send")
    fun sendSms(@Validated @RequestBody smsRequest: SmsRequest) {
        service.sendSms(smsRequest)
    }

}