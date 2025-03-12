package com.mespl.bletms20.data.model

data class LoginRequest(
    var UserName: String? = null,
    var password: String? = null,
    var plantId: Int? = null
)
