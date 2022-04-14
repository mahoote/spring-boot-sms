package com.mahoote.springbootsms.service

import com.mahoote.springbootsms.repos.SmsReceiver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class ReceiverService (@Qualifier("twilioReceiver") @Autowired private val smsReceiver: SmsReceiver) {

    fun receiveSms(requestMap: Map<String, String>) {
        smsReceiver.receiveSms(requestMap)
    }

}