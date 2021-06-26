package com.omairtech.simple.util;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;


/**
 * Created by Mod on 26/06/02021.
 */

public class App extends Application {
    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    public static Application getApplication() {
        return application;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }
}
