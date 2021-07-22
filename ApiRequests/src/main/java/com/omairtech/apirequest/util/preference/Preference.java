package com.omairtech.apirequest.util.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {
    private final SharedPreferences ref;
    private static volatile Preference instance;

    public static Preference getInstance(Context context) {
        if (instance == null)
            instance = new Preference(context);
        return instance;
    }

    private Preference(Context context) {
        this.ref = context.getSharedPreferences("ApiRequestRef", Context.MODE_PRIVATE);
    }


    public void saveBaseUrl(String baseUrl) {
        SharedPreferences.Editor editor = ref.edit();
        editor.putString("baseUrl", baseUrl);
        editor.apply();
    }

    public String getBaseUrl() {
        return ref.getString("baseUrl", "");
    }
}

