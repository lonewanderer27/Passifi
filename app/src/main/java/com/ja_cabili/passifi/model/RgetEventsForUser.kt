package com.ja_cabili.passifi.model

data class RgetEventsForUser(
    val events: List<Event>,
    val success: Boolean,
    val error: String?,
)
