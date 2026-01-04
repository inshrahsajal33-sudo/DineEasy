package com.example.dineeasy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.dineeasy.R;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvUserName = findViewById(R.id.tvUserName);
        tvUserName.setText("Inshrah Sajal");

        CardView cvManageDishes = findViewById(R.id.cvManageDishes);
        CardView cvDailySpecials = findViewById(R.id.cvDailySpecials);
        CardView cvProfile = findViewById(R.id.cvProfile);
        CardView cvAddDish = findViewById(R.id.cvAddDish);

        cvManageDishes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ManageDishesActivity.class);
            startActivity(intent);
        });

        cvDailySpecials.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DailySpecialsActivity.class);
            startActivity(intent);
        });

        cvProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            String userEmail = getIntent().getStringExtra("USER_EMAIL");
            if (userEmail != null) {
                intent.putExtra("USER_EMAIL", userEmail);
            }
            startActivity(intent);
        });

        cvAddDish.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddDishActivity.class);
            startActivity(intent);
        });
    }

    private void showToast(String message) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show();
    }
}