package com.mahoote.springbootsms.repos

interface SmsReceiver {

    fun receiveSms(requestMap: Map<String, String>)

}