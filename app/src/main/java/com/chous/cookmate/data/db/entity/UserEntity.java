package com.chous.cookmate.data.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserEntity {

    @PrimaryKey
    @NonNull
    public String email;

    public String token;

    public UserEntity(@NonNull String email, String token) {
        this.email = email;
        this.token = token;
    }
}
