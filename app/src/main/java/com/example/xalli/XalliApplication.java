package com.example.xalli;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.Context;

import com.google.android.gms.ads.MobileAds; // Importar MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus; // Importar InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener; // Importar OnInitializationCompleteListener

public class XalliApplication extends Application {

    private static final String PREF_NAME = "XalliPrefs";
    private static final String KEY_IS_PREMIUM = "is_premium";

    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
    }

    public boolean isPremiumUser() {
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_IS_PREMIUM, false);
    }

    public void setPremiumUser(boolean isPremium) {
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_IS_PREMIUM, isPremium);
        editor.apply();
    }
}
