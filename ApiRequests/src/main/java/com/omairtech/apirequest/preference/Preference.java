package com.omairtech.apirequest.preference;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Preference {
    private final SharedPreferences ref;

    private static volatile Preference instance;

    public static Preference getInstance(Context context) {
        if (instance == null) {
            instance = new Preference(context);
        }
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

