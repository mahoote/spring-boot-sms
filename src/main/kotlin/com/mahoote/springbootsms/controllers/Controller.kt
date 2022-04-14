package com.mahoote.springbootsms.controllers

import com.mahoote.springbootsms.models.SmsRequest
import com.mahoote.springbootsms.service.ReceiverService
import com.mahoote.springbootsms.service.SenderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1")
class Controller(@Autowired private val senderService: SenderService,
                 @Autowired private val receiverService: ReceiverService) {

    @GetMapping
    fun testGet(): String {
        return "It worked!"
    }

    @PostMapping(value = ["/receive"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE], produces = [MediaType.APPLICATION_ATOM_XML_VALUE])
    fun receiveSms(@RequestParam requestMap: Map<String, String>) {
        receiverService.receiveSms(requestMap)
    }


    @PostMapping("/send")
    fun sendSms(@Validated @RequestBody smsRequest: SmsRequest) {
        senderService.sendSms(smsRequest)
    }

}