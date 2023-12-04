package com.ja_cabili.passifi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int SPLASH_DELAY = 3000; // 5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Delay for 5 seconds and then move to another activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Create an Intent that will start the next activity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

                // Finish this activity
                finish();
            }
        }, SPLASH_DELAY);
    }
}