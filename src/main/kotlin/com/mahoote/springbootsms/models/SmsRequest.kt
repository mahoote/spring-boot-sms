package com.mahoote.springbootsms.models

import com.fasterxml.jackson.annotation.JsonProperty
import org.jetbrains.annotations.NotNull

data class SmsRequest (
    @NotNull @JsonProperty("phoneNumber") val phoneNumber: String, // destination
    @JsonProperty("message") val message: String
        )
