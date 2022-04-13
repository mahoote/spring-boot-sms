package com.mahoote.springbootsms.models

data class SmsRequest (
    val phoneNumber: String, // destination
    val message: String
        )
