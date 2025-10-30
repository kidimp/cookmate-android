package com.chous.cookmate.ui.auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chous.cookmate.domain.model.LoginResult;
import com.chous.cookmate.data.repository.AuthRepository;

/**
 * Хранит и управляет состоянием экрана логина, связывая UI (LoginActivity) и бизнес-логику (AuthRepository).<br>
 * ViewModel не знает, онлайн ли устройство или оффлайн — это забота репозитория.
 */
public class AuthViewModel extends AndroidViewModel {

    private final AuthRepository authRepository;
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    public AuthViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String email, String password) {
        isLoading.setValue(true);

        authRepository.login(email, password, result -> {
            isLoading.postValue(false);
            loginResult.postValue(result);
        });
    }
}

/**
 * LiveData<LoginResult>
 * LiveData — это обёртка над данными, которая:
 * хранит значение (в твоём случае — объект LoginResult);
 * уведомляет Activity или Fragment об изменении этого значения;
 * живёт в соответствии с жизненным циклом (не вызывает onChanged, если Activity в фоне).
 * Иными словами: LiveData = “реактивная переменная” с учётом lifecycle.
 */