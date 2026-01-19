package com.kononovco.haircutselectionbyphoto;

import java.util.Locale;
import android.app.Activity;
import android.content.res.*;
import android.content.Context;

public class AppLanguage {

    private final Context context;
    private final AppData data;

    public AppLanguage(Context context) {
        this.context = context;
        data = new AppData(context);
    }

    private void setLocale(String code) {
        Locale locale = new Locale(code);

        Resources res = context.getResources();
        Configuration config = res.getConfiguration();

        config.setLocale(locale);
        Locale.setDefault(locale);

        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    public void load() {
        String defaultLang = Locale.getDefault().getLanguage();
        String savedLang = data.getString("lang", defaultLang);

        setLocale(savedLang);
    }

    public void save(String code) {
        setLocale(code);

        data.setString("lang", code);
        ((Activity) context).recreate();
    }
}