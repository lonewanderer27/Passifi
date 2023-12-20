package com.ja_cabili.passifi.model

data class RfindUserById(
    val user: User,
    val success: Boolean,
    val error: String?
)
