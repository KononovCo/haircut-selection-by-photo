package com.kononovco.haircutselectionbyphoto;

import android.app.Activity;
import android.content.Intent;

public class Redirect {

    private final String KEY = "query";
    private final Activity activity;

    public Redirect(Activity activity) {
        this.activity = activity;
    }

    public String getQuery() {
        return activity.getIntent().getStringExtra(KEY);
    }

    public void start(Class<?> to) {
        Intent intent = new Intent(activity, to);
        activity.startActivity(intent);
    }

    public void start(Class<?> to, String query) {
        Intent intent = new Intent(activity, to);

        intent.putExtra(KEY, query);
        activity.startActivity(intent);
    }
}