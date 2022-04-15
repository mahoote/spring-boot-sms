package com.mahoote.springbootsms.controllers

import com.mahoote.springbootsms.models.SmsRequest
import com.mahoote.springbootsms.service.ReceiverService
import com.mahoote.springbootsms.service.SenderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE
import org.springframework.http.MediaType.APPLICATION_ATOM_XML_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/sms")
class SmsController(@Autowired private val senderService: SenderService,
                    @Autowired private val receiverService: ReceiverService) {

    @GetMapping
    fun testGet(): String {
        return "It worked!"
    }

    @PostMapping(value = ["/receive"], consumes = [APPLICATION_FORM_URLENCODED_VALUE], produces = [APPLICATION_ATOM_XML_VALUE])
    fun receiveSms(@RequestParam requestMap: Map<String, String>) {
        receiverService.receiveSms(requestMap)
    }


    @PostMapping("/send")
    fun sendSms(@Validated @RequestBody smsRequest: SmsRequest): ResponseEntity<SmsRequest> {
        return senderService.sendSms(smsRequest)
    }

}