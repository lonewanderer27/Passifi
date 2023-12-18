package com.ja_cabili.passifi.repository

import com.ja_cabili.passifi.api.RetrofitInstance
import com.ja_cabili.passifi.model.RSignup
import com.ja_cabili.passifi.model.RfindByEmailAndPassword
import retrofit2.Response

class Repository {
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
}