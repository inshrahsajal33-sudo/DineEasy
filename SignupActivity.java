package com.example.dineeasy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dineeasy.R;
import com.example.dineeasy.database.UserDBHelper;

public class SignupActivity extends AppCompatActivity {

    private EditText etFullName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private UserDBHelper userDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userDBHelper = new UserDBHelper(this);
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        findViewById(R.id.btnSignup).setOnClickListener(v -> handleSignup());
        findViewById(R.id.tvLogin).setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void handleSignup() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (fullName.isEmpty()) {
            etFullName.setError("Full name required");
            return;
        }

        if (email.isEmpty()) {
            etEmail.setError("Email required");
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password required");
            return;
        }

        if (confirmPassword.isEmpty()) {
            etConfirmPassword.setError("Confirm password required");
            return;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords don't match");
            return;
        }

        if (userDBHelper.checkEmailExists(email)) {
            etEmail.setError("Email already registered");
            return;
        }

        if (userDBHelper.addUser(fullName, email, password)) {
            Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            intent.putExtra("USER_EMAIL", email);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        userDBHelper.close();
        super.onDestroy();
    }
}