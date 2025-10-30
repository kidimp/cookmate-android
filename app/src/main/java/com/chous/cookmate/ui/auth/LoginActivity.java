package com.chous.cookmate.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.chous.cookmate.R;
import com.chous.cookmate.ui.main.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Экран логирования<br><br>
 * Пользователь нажал “Войти”	---	AuthViewModel.login() запускает проверку<br>
 * Начинается запрос	---	isLoading.setValue(true) → показывает ProgressBar<br>
 * Ответ с сервера	---	authRepository вызывает callback с LoginResult.success() или LoginResult.error("Неверный пароль")<br>
 * ViewModel обновляет LiveData	---	loginResult.postValue(result)<br>
 * Activity получает обновление	---	observe(...) → показывает Toast / открывает MainActivity<br>
 */
public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private Button loginButton;
    private TextView textRegister;
    private ProgressBar progressBar;

    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Инициализация ViewModel
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // Находим элементы
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);
        textRegister = findViewById(R.id.textRegister);
        progressBar = findViewById(R.id.progressBar);

        // Подписываемся на LiveData
        authViewModel.getIsLoading().observe(this, isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            loginButton.setEnabled(!isLoading);
        });

        authViewModel.getLoginResult().observe(this, result -> {
            if (result.isSuccess()) {
                Toast.makeText(this, "Добро пожаловать!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, result.getErrorMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Обработчики кнопок
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Введите email и пароль", Toast.LENGTH_SHORT).show();
            } else {
                authViewModel.login(email, password);
            }
        });

        textRegister.setOnClickListener(v -> {
            Toast.makeText(this, "Переход к регистрации (пока не реализовано)", Toast.LENGTH_SHORT).show();
        });
//        textRegister.setOnClickListener(v -> {
//            Intent intent = new Intent(this, RegisterActivity.class);
//            startActivity(intent);
//        });
    }
}

