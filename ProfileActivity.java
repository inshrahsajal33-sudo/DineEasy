package com.example.dineeasy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dineeasy.R;
import com.example.dineeasy.database.UserDBHelper;

public class ProfileActivity extends AppCompatActivity {

    private UserDBHelper userDBHelper;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userDBHelper = new UserDBHelper(this);

        userEmail = getIntent().getStringExtra("USER_EMAIL");
        if (userEmail == null || userEmail.isEmpty()) {
            userEmail = "inshrahSajal";
        }

        TextView tvUserName = findViewById(R.id.tvUserName);
        TextView tvUserEmail = findViewById(R.id.tvUserEmail);
        TextView tvEditProfile = findViewById(R.id.tvEditProfile);
        TextView tvLogout = findViewById(R.id.tvLogout);
        TextView tvBack = findViewById(R.id.tvBack);

        String fullName = userDBHelper.getUserFullName(userEmail);
        if (fullName != null && !fullName.isEmpty()) {
            tvUserName.setText(fullName);
        } else {
            tvUserName.setText("Inshrah Sajal");
        }

        tvUserEmail.setText(userEmail);

        tvEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
            intent.putExtra("USER_EMAIL", userEmail);
            startActivity(intent);
        });

        tvLogout.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        tvBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onDestroy() {
        if (userDBHelper != null) {
            userDBHelper.close();
        }
        super.onDestroy();
    }
}