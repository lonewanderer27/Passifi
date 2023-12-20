package com.ja_cabili.passifi.model

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

data class Event(
    val id: Int,
    val avatar: String?,
    val description: String,
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val organizer: String,
    val organizer_email: String,
    val organizer_approval: String,
    val user_id: Int,
    val invite_code: String,
    val current_attendees: Int?,
    val total_attendees: Int?,
    val guests: List<Guest>?,
) {
    fun getFormattedDate(): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val parsedDate = inputFormat.parse(date)
            Log.d("Event", "Formatted date: ${outputFormat.format(parsedDate!!)}")
            outputFormat.format(parsedDate)
        } catch (e: ParseException) {
            Log.d("Event", "Error parsing date: $e")
            date // Return original date in case of parsing error
        }
    }
}
