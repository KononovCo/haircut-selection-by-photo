package com.kononovco.haircutselectionbyphoto;

import android.content.*;

public class AppData {

    private final SharedPreferences settings;
    private final SharedPreferences.Editor editor;

    public AppData(Context context) {
        settings = context.getSharedPreferences("AppData", Context.MODE_PRIVATE);
        editor = settings.edit();
    }

    public String getString(String key, String defaultValue) {
        return settings.getString(key, defaultValue);
    }

    public void setString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }
}