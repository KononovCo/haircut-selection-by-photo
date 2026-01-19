package com.kononovco.haircutselectionbyphoto;

import android.graphics.*;

public class BitmapShape extends Shape {

    private Bitmap bitmap;

    public BitmapShape(float left, float top, float right, float bottom) {
        super(left, top, right, bottom);
    }

    @Override
    public void draw(Canvas canvas) {
        if (bitmap != null) {
            RectF rect = new RectF(getLeft(), getTop(), getRight(), getBottom());
            canvas.drawBitmap(bitmap, null, rect, null);
        }
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}