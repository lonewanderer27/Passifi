package com.ja_cabili.passifi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ja_cabili.passifi.model.RSignup
import com.ja_cabili.passifi.model.RfindByEmailAndPassword
import com.ja_cabili.passifi.repository.Repository
import kotlinx.coroutines.launch
import com.ja_cabili.passifi.model.User
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {
    val loginResponse: MutableLiveData<Response<RfindByEmailAndPassword>> = MutableLiveData()
    val signupResponse: MutableLiveData<Response<RSignup>> = MutableLiveData()
    val user: MutableLiveData<User> = MutableLiveData()

    fun findUserByEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            val response = repository.findUserByEmailAndPassword(email, password)
            loginResponse.value = response

            if (response.isSuccessful) {
                user.value = response.body()?.user
            }
        }
    }

    fun signup(name: String, email: String, password: String) {
        viewModelScope.launch {
            val response = repository.signup(name, email, password)
            signupResponse.value = response

            if (response.isSuccessful) {
                user.value = response.body()?.user
            }
        }
    }
}