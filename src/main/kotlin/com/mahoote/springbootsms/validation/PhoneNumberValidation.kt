package com.mahoote.springbootsms.validation

object PhoneNumberValidation {

    fun validatePhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.matches(Regex("((\\+|00)[0-9]{2})?[0-9]{8}"))
    }

}