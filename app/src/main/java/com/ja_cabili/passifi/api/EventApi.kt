package com.ja_cabili.passifi.api

import com.ja_cabili.passifi.model.RjoinUsingInviteCode
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface EventApi {
    @POST("events/join")
    suspend fun joinUsingInviteCode(
        @Body request: Map<String, String>
    ): Response<RjoinUsingInviteCode>
}