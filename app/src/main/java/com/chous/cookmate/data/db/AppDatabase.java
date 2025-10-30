package com.chous.cookmate.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.chous.cookmate.data.db.dao.AuthDao;
import com.chous.cookmate.data.db.entity.UserEntity;

/**
 * Создаёт и предоставляет Room DAO
 */
@Database(entities = {UserEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract AuthDao authDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "cookmate_db"
            ).build();
        }
        return instance;
    }
}
