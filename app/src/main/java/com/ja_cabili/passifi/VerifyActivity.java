package com.ja_cabili.passifi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class VerifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        // Back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement the functionality to go back to the previous screen
                finish();
            }
        });

        // Submit button
        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement the logic to verify the entered OTP
                // Get the entered OTP from EditText boxes
                EditText otpBox1 = findViewById(R.id.otpBox1);
                EditText otpBox2 = findViewById(R.id.otpBox2);
                EditText otpBox3 = findViewById(R.id.otpBox3);
                EditText otpBox4 = findViewById(R.id.otpBox4);

                String otp = otpBox1.getText().toString() +
                        otpBox2.getText().toString() +
                        otpBox3.getText().toString() +
                        otpBox4.getText().toString();

                // Validate or process the entered OTP as needed
                // For example, send it for verification or further processing
            }
        });
    }
}