package com.ja_cabili.passifi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Find views by their IDs
        TextView signInText = findViewById(R.id.signInText);
        TextView signUpButton = findViewById(R.id.signUpButton);

        // Add click listener to "Sign In" TextView
        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to LoginActivity
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Finish current activity to prevent going back on pressing back button
            }
        });


        // Add click listener to "Sign Up" Button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your sign-up logic here
                // For example, you can validate inputs and create a new account
            }
        });
    }
}