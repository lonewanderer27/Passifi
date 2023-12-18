package com.ja_cabili.passifi

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Delay for 5 seconds and then move to another activity
        Handler().postDelayed({ // Create an Intent that will start the next activity
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)

            // Finish this activity
            finish()
        }, SPLASH_DELAY.toLong())
    }

    companion object {
        private const val SPLASH_DELAY = 3000 // 5 seconds
    }
}