package com.kononovco.haircutselectionbyphoto;

import android.graphics.*;

public final class BitmapUtils {

    private BitmapUtils() {}

    public static int getWidthByHeight(Bitmap bitmap, int height) {
        return (int) (height / ((float) bitmap.getHeight() / bitmap.getWidth()));
    }

    public static int getHeightByWidth(Bitmap bitmap, int width) {
        return (int) (width / ((float) bitmap.getWidth() / bitmap.getHeight()));
    }

    public static Bitmap scale(Bitmap original, int width, int height) {
        Matrix matrix = new Matrix();

        int originalWidth = original.getWidth();
        int originalHeight = original.getHeight();

        int scaleWidth = Math.max(width, originalWidth) / originalWidth;
        int scaleHeight = Math.max(height, originalHeight) / originalHeight;

        matrix.setScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(original, 0, 0, originalWidth, originalHeight, matrix, true);
    }
}