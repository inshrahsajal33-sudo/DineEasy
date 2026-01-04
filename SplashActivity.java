package com.example.dineeasy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dineeasy.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        TextView appName = findViewById(R.id.appName);
        TextView developerName = findViewById(R.id.developerName);

        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        appName.startAnimation(fadeIn);

        Animation slideUp = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        developerName.startAnimation(slideUp);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_DURATION);
    }
}