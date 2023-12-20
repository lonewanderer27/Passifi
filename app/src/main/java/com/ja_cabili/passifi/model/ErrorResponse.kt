package com.ja_cabili.passifi.model

data class ErrorResponse(
    val error: String, val message: String?, val success: Boolean
)
