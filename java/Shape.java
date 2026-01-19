package com.kononovco.haircutselectionbyphoto;

import android.graphics.*;

public abstract class Shape {

    private final int CONTROL_POINT_SIZE = 15;

    private final RectF bounds;
    private final PointF pivot;

    private EditMode editMode;
    private int controlPointIndex;

    public Shape(float left, float top, float right, float bottom) {
        bounds = new RectF(left, top, right, bottom);
        pivot = new PointF();

        editMode = EditMode.Move;
        controlPointIndex = -1;
    }

    private PointF[] getControlPoints() {
        return new PointF[] {
                new PointF(bounds.left, bounds.top),
                new PointF(bounds.right, bounds.top),

                new PointF(bounds.left, bounds.bottom),
                new PointF(bounds.right, bounds.bottom),

                new PointF(bounds.left, bounds.centerY()),
                new PointF(bounds.right, bounds.centerY()),

                new PointF(bounds.centerX(), bounds.top),
                new PointF(bounds.centerX(), bounds.bottom)
        };
    }

    public float getLeft() {
        return bounds.left;
    }

    public float getTop() {
        return bounds.top;
    }

    public float getRight() {
        return bounds.right;
    }

    public float getBottom() {
        return bounds.bottom;
    }

    public EditMode getEditMode() {
        return editMode;
    }

    public boolean containsShape(float x, float y) {
        boolean containsX = x > bounds.left && x < bounds.right;
        boolean containsY = y > bounds.top && y < bounds.bottom;

        return containsX && containsY;
    }

    public boolean containsControlPoint(float x, float y) {
        PointF[] points = getControlPoints();

        for (int i = 0; i < points.length; i++) {
            if (Math.hypot(points[i].x - x, points[i].y - y) <= CONTROL_POINT_SIZE * 2) {
                controlPointIndex = i;
                return true;
            }
        }

        return false;
    }

    public void setPivotPoint(float x, float y) {
        pivot.x = x - bounds.left;
        pivot.y = y - bounds.top;
    }

    public void setEditMode(EditMode editMode) {
        this.editMode = editMode;
    }

    public void move(float x, float y) {
        float offsetX = x - pivot.x;
        float offsetY = y - pivot.y;

        bounds.offsetTo(offsetX, offsetY);
    }

    public void resize(float x, float y) {
        int i = controlPointIndex;

        float left = Math.min(x, bounds.right - 100);
        float top = Math.min(y, bounds.bottom - 100);

        float right = Math.max(x, bounds.left + 100);
        float bottom = Math.max(y, bounds.top + 100);

        if (i == 0 || i == 2 || i == 4) bounds.left = left;
        if (i == 0 || i == 1 || i == 6) bounds.top = top;

        if (i == 1 || i == 3 || i == 5) bounds.right = right;
        if (i == 2 || i == 3 || i == 7) bounds.bottom = bottom;
    }

    public void select(Canvas canvas) {
        Paint paint = new Paint();

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

        for (PointF point : getControlPoints()) {
            canvas.drawCircle(point.x, point.y, CONTROL_POINT_SIZE, paint);
        }

        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawRect(bounds.left, bounds.top, bounds.right, bounds.bottom, paint);
    }

    abstract public void draw(Canvas canvas);
}