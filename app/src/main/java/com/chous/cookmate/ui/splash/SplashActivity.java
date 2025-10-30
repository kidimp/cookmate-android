package com.chous.cookmate.ui.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.chous.cookmate.R;
import com.chous.cookmate.ui.auth.LoginActivity;
import com.chous.cookmate.ui.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY = 1200; // задержка 1.2 секунды

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Читаем токен из SharedPreferences
        SharedPreferences prefs = getSharedPreferences("auth_prefs", MODE_PRIVATE);
        String token = prefs.getString("auth_token", null);

        // Проверяем, есть ли сохранённый токен
        Intent intent;
        if (token != null && !token.isEmpty()) {
            // Пользователь уже залогинен
            intent = new Intent(this, MainActivity.class);
        } else {
            // Пользователь не залогинен
            intent = new Intent(this, LoginActivity.class);
        }

        // Стартуем нужную активность
        startActivity(intent);
        finish();
    }
}
