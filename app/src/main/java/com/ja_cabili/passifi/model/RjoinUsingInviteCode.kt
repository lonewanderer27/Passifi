package com.ja_cabili.passifi.model

data class RjoinUsingInviteCode(
    val event: Event,
    val guest: Guest,
    val success: Boolean,
    val error: String,
    val message: String
)
