package com.ja_cabili.passifi.api

import com.ja_cabili.passifi.model.RSignup
import com.ja_cabili.passifi.model.RfindByEmailAndPassword
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("users/getByEmailAndPassword")
    suspend fun findByEmailAndPassword(
        @Body request: Map<String, String>
    ): Response<RfindByEmailAndPassword>

    @POST("users/signup")
    suspend fun signup(
        @Body request: Map<String, String>
    ): Response<RSignup>
}