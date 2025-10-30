package com.chous.cookmate.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.chous.cookmate.data.db.AppDatabase;
import com.chous.cookmate.data.db.dao.AuthDao;
import com.chous.cookmate.data.db.entity.UserEntity;
import com.chous.cookmate.data.remote.dto.LoginResponse;
import com.chous.cookmate.domain.model.LoginResult;
import com.chous.cookmate.data.remote.ApiClient;
import com.chous.cookmate.data.remote.AuthApiService;
import com.chous.cookmate.utils.NetworkUtils;

import retrofit2.Response;

/**
 * Инкапсулирует источник данных: решает где и как взять информацию —
 * из сети, локальной БД, кэша и т. д.<br>
 * ViewModel не знает, онлайн ли устройство или оффлайн — это забота репозитория.
 */
public class AuthRepository {

    private final Context context;
    private final AuthApiService apiService;
    private final AuthDao authDao;

    public AuthRepository(Context context) {
        this.context = context;
        apiService = ApiClient.getAuthApiService();
        authDao = AppDatabase.getInstance(context).authDao();
    }

    public interface LoginCallback {
        void onResult(LoginResult result);
    }

    public void login(String email, String password, LoginCallback callback) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            // 🌐 Онлайн логин
            new Thread(() -> {
                try {
                    Response<LoginResponse> response = apiService.login(email, password).execute();
                    if (response.isSuccessful() && response.body() != null) {
                        String token = response.body().getToken();
                        saveToken(token);
                        authDao.insertUser(new UserEntity(email, token));
                        callback.onResult(new LoginResult(true, null));
                    } else {
                        callback.onResult(new LoginResult(false, "Неверные данные"));
                    }
                } catch (Exception e) {
                    callback.onResult(new LoginResult(false, e.getMessage()));
                }
            }).start();
        } else {
            // 📴 Оффлайн логин
            new Thread(() -> {
                UserEntity user = authDao.getUserByEmail(email);
                if (user != null) {
                    callback.onResult(new LoginResult(true, null));
                } else {
                    callback.onResult(new LoginResult(false, "Нет сети и нет сохранённой сессии"));
                }
            }).start();
        }
    }

    private void saveToken(String token) {
        SharedPreferences prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
        prefs.edit().putString("auth_token", token).apply();
    }
}
