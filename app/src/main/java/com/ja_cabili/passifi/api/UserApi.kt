package com.ja_cabili.passifi.api

import com.ja_cabili.passifi.model.RSignup
import com.ja_cabili.passifi.model.RfindByEmailAndPassword
import com.ja_cabili.passifi.model.RfindUserById
import com.ja_cabili.passifi.model.RgetEventsForUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    @GET("users/{id}")
    suspend fun getUser(
        @Path("id") id: Int
    ): Response<RfindUserById>

    @POST("users/getByEmailAndPassword")
    suspend fun findByEmailAndPassword(
        @Body request: Map<String, String>
    ): Response<RfindByEmailAndPassword>

    @POST("users/signup")
    suspend fun signup(
        @Body request: Map<String, String>
    ): Response<RSignup>

    @GET("users/{id}/events/approved")
    suspend fun getApprovedEventsForUser(
        @Path("id") id: Int
    ): Response<RgetEventsForUser>

    @GET("users/{id}/events/pending")
    suspend fun getPendingEventsForUser(
        @Path("id") id: Int
    ): Response<RgetEventsForUser>
}