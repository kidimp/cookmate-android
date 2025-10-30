package com.chous.cookmate.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("token")
    private String token;

    @SerializedName("user")
    private UserDTO user;

    public boolean isSuccess() {
        return success;
    }

    public String getToken() {
        return token;
    }

    public UserDTO getUser() {
        return user;
    }
}
