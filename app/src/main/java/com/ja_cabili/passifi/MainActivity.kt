package com.ja_cabili.passifi

import MainViewModelFactory
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ja_cabili.passifi.model.User
import com.ja_cabili.passifi.repository.Repository

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the ViewModel
        val viewModelFactory = MainViewModelFactory(Repository(), applicationContext)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        // Check if there is a saved users
        val savedUser = viewModel.getUserFromLocalStorage()
        if (savedUser != null) {
            // There is a saved user, attempt to find the user
            viewModel.findUserById(savedUser.id)

            // Observe the login response
            viewModel.user.observe(this) { user ->
                if (user != null) {
                    Log.d("com.ja_cabili.passifi.MainActivity", "User found")
                    // welcome the user
                    Toast.makeText(this, "Welcome back, ${user.name}", Toast.LENGTH_LONG).show();

                    // User found, start HomeActivity
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish() // Finish this activity
                } else {
                    Log.d("com.ja_cabili.passifi.MainActivity", "Saved user not found in DB")
                    Toast.makeText(this, "Saved user not found in DB", Toast.LENGTH_LONG).show();

                    // User not found, start LoginActivity
                    startLoginActivity()
                }
            }
        } else {
            // No saved user, start LoginActivity
            Log.d("com.ja_cabili.passifi.MainActivity", "No saved user")
            startLoginActivity()
        }
    }

    private fun startLoginActivity() {
        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Finish this activity
        }, SPLASH_DELAY.toLong())
    }

    companion object {
        private const val SPLASH_DELAY = 3000 // 5 seconds
    }
}
