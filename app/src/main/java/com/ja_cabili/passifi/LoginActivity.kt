package com.ja_cabili.passifi

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ja_cabili.passifi.repository.Repository

class LoginActivity : AppCompatActivity() {

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val viewModel = MainViewModelFactory(Repository()).create(MainViewModel::class.java)

        // Initialize ProgressDialog
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")

        // Initialize views
        val signInButton = findViewById<Button>(R.id.signInButton)

        // Set click listener for Sign In button
        signInButton.setOnClickListener {
            val email = findViewById<TextView>(R.id.emailInput).text.toString()
            val password = findViewById<TextView>(R.id.passwordInput).text.toString()

            // Show ProgressDialog
            progressDialog.show()

            viewModel.findUserByEmailAndPassword(email, password)
        }

        // Set click listener for "Sign Up" text
        val signUpText = findViewById<TextView>(R.id.signUpText)
        signUpText.setOnClickListener { // Redirect to SignupActivity
            // Redirect to signup activity
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }

        viewModel.loginResponse.observe(this) { response ->
            // Dismiss ProgressDialog
            progressDialog.dismiss()

            if (response.isSuccessful) {
                Log.d("Response", response.body()?.user.toString())
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(intent)
            } else {
                Log.d("Error: ", response.errorBody()?.string().toString())
                Toast.makeText(this, "Error: ${response.errorBody()?.string()}", Toast.LENGTH_LONG).show()
            }
        }
    }
}