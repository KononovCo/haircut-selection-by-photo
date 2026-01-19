package com.kononovco.haircutselectionbyphoto;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;

public class Permission {

    private final Context context;

    public Permission(Context context) {
        this.context = context;
    }

    public boolean isGranted(String permission) {
        int request = ActivityCompat.checkSelfPermission(context, permission);
        int granted = PackageManager.PERMISSION_GRANTED;

        return request == granted;
    }

    public void request(String permission) {
        Activity activity = (Activity) context;
        String[] permissions = {permission};

        ActivityCompat.requestPermissions(activity, permissions, 1);
    }
}