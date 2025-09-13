package org.example;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

public interface Tools {
    void install(Canvas canvas, GraphicsContext graphics, ColorPicker colorPicker);
}
