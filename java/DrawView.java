package com.kononovco.haircutselectionbyphoto;

import android.view.*;
import android.graphics.*;
import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;

public class DrawView extends View {

    private Bitmap saved;
    private Bitmap background;

    private Canvas canvas;

    private BitmapShape shape;
    private ShapeEditor editor;

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(@NonNull Canvas c) {
        super.onDraw(c);

        if (canvas != null) {
            canvas.drawRGB(0, 0, 0);
            c.drawBitmap(saved, getCenterX(), getCenterY(), null);

            canvas.drawBitmap(background, 0, 0, null);
            editor.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (canvas != null) {
            float x = event.getX() - getCenterX();
            float y = event.getY() - getCenterY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    editor.click(x, y);
                    break;

                case MotionEvent.ACTION_MOVE:
                    editor.edit(x, y);
                    break;
            }

            invalidate();
        }

        return true;
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        if (background != null) {
            int adaptiveWidth = BitmapUtils.getWidthByHeight(background, height);
            int adaptiveHeight = BitmapUtils.getHeightByWidth(background, width);

            adaptiveWidth = Math.min(adaptiveWidth, width);
            adaptiveHeight = Math.min(adaptiveHeight, height);

            saved = Bitmap.createBitmap(adaptiveWidth, adaptiveHeight, Bitmap.Config.ARGB_8888);
            background = Bitmap.createScaledBitmap(background, adaptiveWidth, adaptiveHeight, false);

            canvas = new Canvas(saved);

            shape = new BitmapShape(100, 100, adaptiveWidth - 100, adaptiveHeight / 2f);
            editor = new ShapeEditor();
        }
    }

    private float getCenterX() {
        return (getWidth() - canvas.getWidth()) / 2f;
    }

    private float getCenterY() {
        return (getHeight() - canvas.getHeight()) / 2f;
    }

    public Bitmap getSaved() {
        return saved;
    }

    public void setBackground(Bitmap background) {
        this.background = background;
        invalidate();
    }

    public void createBitmapShape(Bitmap bitmap) {
        shape.setBitmap(bitmap);
        shape.setEditMode(EditMode.Move);

        editor.set(shape);
        invalidate();
    }
}