package com.ja_cabili.passifi.repository

import com.ja_cabili.passifi.api.RetrofitInstance
import com.ja_cabili.passifi.model.RSignup
import com.ja_cabili.passifi.model.RfindByEmailAndPassword
import com.ja_cabili.passifi.model.RfindUserById
import com.ja_cabili.passifi.model.RgetEventsForUser
import com.ja_cabili.passifi.model.RjoinUsingInviteCode
import retrofit2.Response

class Repository {

    suspend fun findUserById(id: Int): Response<RfindUserById> {
        return RetrofitInstance.userApi.getUser(id)
    }

    suspend fun findUserByEmailAndPassword(email: String, password: String): Response<RfindByEmailAndPassword> {
        return RetrofitInstance.userApi.findByEmailAndPassword(
            mapOf(
                "email" to email,
                "password" to password
            )
        )
    }

    suspend fun signup(name: String, email: String, password: String): Response<RSignup> {
        return RetrofitInstance.userApi.signup(
            mapOf(
                "name" to name,
                "email" to email,
                "password" to password
            )
        )
    }

    suspend fun getApprovedEventsForUser(id: Int): Response<RgetEventsForUser> {
        return RetrofitInstance.userApi.getApprovedEventsForUser(id)
    }

    suspend fun getPendingEventsForUser(id: Int): Response<RgetEventsForUser> {
        return RetrofitInstance.userApi.getPendingEventsForUser(id)
    }

    suspend fun joinUsingInviteCode(inviteCode: String, userId: Int): Response<RjoinUsingInviteCode> {
        return RetrofitInstance.eventApi.joinUsingInviteCode(
            mapOf(
                "invite_code" to inviteCode,
                "user_id" to userId.toString()
            )
        )
    }
}