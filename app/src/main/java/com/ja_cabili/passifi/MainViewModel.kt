package com.ja_cabili.passifi

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.ja_cabili.passifi.model.RSignup
import com.ja_cabili.passifi.model.RfindByEmailAndPassword
import com.ja_cabili.passifi.repository.Repository
import kotlinx.coroutines.launch
import com.ja_cabili.passifi.model.User
import com.ja_cabili.passifi.model.Event
import com.ja_cabili.passifi.model.RfindUserById
import com.ja_cabili.passifi.model.RgetEventsForUser
import com.ja_cabili.passifi.model.RjoinUsingInviteCode
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainViewModel(private val repository: Repository, private val context: Context): ViewModel() {
    val loginResponse: MutableLiveData<Response<RfindByEmailAndPassword>> = MutableLiveData()
    val signupResponse: MutableLiveData<Response<RSignup>> = MutableLiveData()
    val eventsResponse: MutableLiveData<Response<RgetEventsForUser>> = MutableLiveData()
    val pendingEventsResponse: MutableLiveData<Response<RgetEventsForUser>> = MutableLiveData()
    val eventResponse: MutableLiveData<Response<RjoinUsingInviteCode>> = MutableLiveData()
    val userResponse: MutableLiveData<Response<RfindUserById>> = MutableLiveData()

    val user: MutableLiveData<User> = MutableLiveData()
    val events: MutableLiveData<List<Event>> = MutableLiveData()
    val pendingEvents: MutableLiveData<List<Event>> = MutableLiveData()
    val event: MutableLiveData<Event> = MutableLiveData()

    fun findUserById(id: Int) {
        viewModelScope.launch {
            val response = repository.findUserById(id)
            user.value = response.body()?.user

            if (response.isSuccessful) {
                user.value = response.body()?.user
                saveUserToLocalStorage(user.value!!)
            }
        }
    }

    fun findUserByEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            val response = repository.findUserByEmailAndPassword(email, password)
            loginResponse.value = response

            if (response.isSuccessful) {
                user.value = response.body()?.user
                saveUserToLocalStorage(user.value!!)
            }
        }
    }

    fun signup(name: String, email: String, password: String) {
        viewModelScope.launch {
            val response = repository.signup(name, email, password)
            signupResponse.value = response

            if (response.isSuccessful) {
                user.value = response.body()?.user
                saveUserToLocalStorage(user.value!!)
            }
        }
    }

    fun joinUsingInviteCode(invite_code: String) {
        viewModelScope.launch {
            val response = repository.joinUsingInviteCode(invite_code, user.value!!.id)
            eventResponse.value = response
            Log.d("MainViewModel", response.body().toString())

            if (response.isSuccessful) {
                event.value = response.body()?.event
            }
        }
    }

    fun getApprovedEventsForUser() {
        viewModelScope.launch {
            val response = repository.getApprovedEventsForUser(user.value!!.id)
            eventsResponse.value = response

            if (response.isSuccessful) {
                events.value = response.body()?.events
                Log.d("All Approved Events", events.value.toString())
            }
        }
    }

    fun getPendingEventsForUser() {
        viewModelScope.launch {
            val response = repository.getPendingEventsForUser(user.value!!.id)
            eventsResponse.value = response

            if (response.isSuccessful) {
                pendingEvents.value = response.body()?.events
                Log.d("All Pending Events", events.value.toString())
            }
        }
    }

    private fun saveUserToLocalStorage(user: User) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val userJson = gson.toJson(user)
        editor.putString("user", userJson)
        editor.apply()
    }

    fun getUserFromLocalStorage(): User? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val gson = Gson()
        val userJson = sharedPreferences.getString("user", null)
        return gson.fromJson(userJson, User::class.java)
    }

    // Function to get the current date in the format "YYYY-MM-DD"
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Calendar.getInstance().time)
    }

    init {
        val savedUser = getUserFromLocalStorage()
        savedUser?.let {
            user.value = it
        }
    }
}