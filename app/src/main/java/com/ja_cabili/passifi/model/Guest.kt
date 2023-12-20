package com.ja_cabili.passifi.model

data class Guest(
    val id: Int,
    val event_id: Int,
    val user_id: Int,
    val approved: Boolean,
    val pending: Boolean,
) {
    fun toJsonString(): String {
        return "{ \"id\": $id, \"event_id\": $event_id, \"user_id\": $user_id, \"approved\": $approved, \"pending\": $pending }";
    }
}