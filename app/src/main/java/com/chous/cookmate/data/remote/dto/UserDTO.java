package com.chous.cookmate.data.remote.dto;

import com.google.gson.annotations.SerializedName;

public class UserDTO {

    @SerializedName("id")
    private int id;

    @SerializedName("email")
    private String email;

    @SerializedName("name")
    private String name;

    // Геттеры
    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
