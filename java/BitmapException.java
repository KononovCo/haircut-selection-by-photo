package com.kononovco.haircutselectionbyphoto;

import android.content.Context;

public class BitmapException extends Exception {

    public static final int FORMAT_ERROR = 1;
    public static final int SIZE_ERROR = 2;
    public static final int EXTENSION_ERROR = 3;

    private final int code;

    public BitmapException(int code) {
        this.code = code;
    }

    public String getMessage(Context context) {
        switch (code) {
            case FORMAT_ERROR:
                return context.getString(R.string.format_error);

            case SIZE_ERROR:
                return context.getString(R.string.size_error);

            case EXTENSION_ERROR:
                return context.getString(R.string.extension_error);
        }

        return null;
    }
}