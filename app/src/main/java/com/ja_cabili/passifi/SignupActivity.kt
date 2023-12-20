package com.ja_cabili.passifi

import MainViewModelFactory
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.ja_cabili.passifi.model.ErrorResponse
import com.ja_cabili.passifi.repository.Repository

class SignupActivity : AppCompatActivity() {
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize ProgressDialog
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")

        val viewModelFactory = MainViewModelFactory(Repository(), applicationContext)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        // Find views by their IDs
        val signInText = findViewById<TextView>(R.id.signInText)
        val signUpButton = findViewById<TextView>(R.id.signUpButton)
        val passwordInput = findViewById<TextView>(R.id.passwordInput)
        val confirmPasswordInput = findViewById<TextView>(R.id.confirmPasswordInput)

        // Add click listener to "Sign In" TextView
        signInText.setOnClickListener {
            // Redirect to LoginActivity
            finish() // Finish current activity to prevent going back on pressing back button
        }


        // Add click listener to "Sign Up" Button
        signUpButton.setOnClickListener {
            val password = passwordInput.text.toString()
            val confirmPassword = confirmPasswordInput.text.toString()

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Show ProgressDialog
            progressDialog.show()

            viewModel.signup(
                findViewById<TextView>(R.id.nameInput).text.toString(),
                findViewById<TextView>(R.id.emailInput).text.toString(),
                password)
        }

        viewModel.signupResponse.observe(this) { response ->
            // Dismiss ProgressDialog
            progressDialog.dismiss()

            if (response.isSuccessful) {
                Log.d("Response", response.body()?.user.toString())
                val intent = Intent(this@SignupActivity, HomeActivity::class.java)
                startActivity(intent)
            } else {
                val errorResponse: ErrorResponse? = response.errorBody()?.string()?.let { Gson().fromJson(it, ErrorResponse::class.java) }
                Toast.makeText(this, errorResponse?.error, Toast.LENGTH_LONG).show()
                Toast.makeText(this, "Error: ${response.errorBody()?.string()}", Toast.LENGTH_LONG).show()
            }
        }
    }
}