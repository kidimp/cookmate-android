package com.chous.cookmate.domain.model;

/**
 * Модель результата логирования
 */
public class LoginResult {

    private final boolean success;
    private final String errorMessage;

    public LoginResult(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public static LoginResult success() {
        return new LoginResult(true, null);
    }

    public static LoginResult error(String message) {
        return new LoginResult(false, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}