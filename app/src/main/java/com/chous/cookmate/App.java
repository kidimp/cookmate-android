package com.chous.cookmate;

import android.app.Application;

/**
 * Базовая точка входа в Android-приложение, создаётся один раз при старте приложения (до любой Activity).
 */
public class App extends Application {
    private static App mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static App getContext() {
        return mContext;
    }
}
