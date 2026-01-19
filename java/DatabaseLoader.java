package com.kononovco.haircutselectionbyphoto;

import android.content.Context;

public class DatabaseLoader {

    private final Context context;
    private final String query;

    private int[] data;

    public DatabaseLoader(Context context, String query) {
        this.context = context;
        this.query = query;

        data = new int[] {};
    }

    private boolean equals(int strId) {
        return query != null && query.equals(context.getString(strId));
    }

    public int[] getData() {
        return data;
    }

    public boolean load() {
        Database database = new Database();

        if (equals(R.string.short_hair)) data = database.getShortHair();
        else if (equals(R.string.medium_hair)) data = database.getMediumHair();
        else if (equals(R.string.long_hair)) data = database.getLongHair();

        return data.length != 0;
    }
}