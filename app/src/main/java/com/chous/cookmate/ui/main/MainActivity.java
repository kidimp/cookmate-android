package com.chous.cookmate.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.chous.cookmate.R;

/**
 * Главный экран приложения — то, куда попадает пользователь после успешного входа (логина).<br>
 * LoginActivity → при успешном входе → startActivity(new Intent(this, MainActivity.class))
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}