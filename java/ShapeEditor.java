package com.kononovco.haircutselectionbyphoto;

import android.graphics.Canvas;

public class ShapeEditor {

    private Shape[] shapes;

    public ShapeEditor() {
        shapes = new Shape[] {};
    }

    public void set(Shape shape) {
        shapes = new Shape[] {shape};
    }

    public void draw(Canvas canvas) {
        for (Shape shape : shapes) {
            shape.draw(canvas);

            if (shape.getEditMode() != EditMode.None) {
                shape.select(canvas);
            }
        }
    }

    public void click(float x, float y) {
        for (Shape shape : shapes) {
            if (shape.containsControlPoint(x, y)) {
                shape.setEditMode(EditMode.Resize);
            }

            else {
                if (shape.containsShape(x, y)) {
                    shape.setPivotPoint(x, y);
                    shape.setEditMode(EditMode.Move);
                }

                else shape.setEditMode(EditMode.None);
            }
        }
    }

    public void edit(float x, float y) {
        for (Shape shape : shapes) {
            switch (shape.getEditMode()) {
                case Move:
                    shape.move(x, y);
                    break;

                case Resize:
                    shape.resize(x, y);
                    break;
            }
        }
    }
}