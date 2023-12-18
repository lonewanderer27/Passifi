package com.ja_cabili.passifi.model

data class RSignup(
    val user: User,
    val token: String,
    val success: Boolean,
    val error: String?,
)
