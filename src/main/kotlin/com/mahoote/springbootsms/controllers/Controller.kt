package com.mahoote.springbootsms.controllers

import com.mahoote.springbootsms.models.SmsRequest
import com.mahoote.springbootsms.service.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("api/v1")
class Controller(@Autowired private val service: Service) {

    @GetMapping
    fun testGet(): String {
        return "It worked!"
    }

    @PostMapping(value = ["/receive"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE], produces = [MediaType.APPLICATION_ATOM_XML_VALUE])
    fun receiveSms(@RequestBody request: HttpServletRequest) {
        service.receiveSms(request)
    }


    @PostMapping("/send")
    fun sendSms(@Validated @RequestBody smsRequest: SmsRequest) {
        service.sendSms(smsRequest)
    }

}