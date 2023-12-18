package com.ja_cabili.passifi.model

data class RfindByEmailAndPassword(
    val user: User,
    val success: Boolean,
    val error: String?,
)
