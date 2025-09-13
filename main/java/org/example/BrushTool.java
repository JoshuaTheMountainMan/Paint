package org.example;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

public class BrushTool extends SizeableTool{
    double lastX;
    double lastY;

    public BrushTool(){setSize(1);}
    public BrushTool(double size){setSize(size);}


    @Override
    public void install(Canvas canvas, GraphicsContext graphics, ColorPicker colorPicker) {
        canvas.setOnMousePressed(mouseEvent -> {
            graphics.beginPath();
            graphics.moveTo(mouseEvent.getX(),mouseEvent.getY());

            super.install(canvas,graphics,colorPicker);
        });

        canvas.setOnMouseDragged(mouseEvent -> {
            graphics.lineTo(mouseEvent.getX(),mouseEvent.getY());
            graphics.stroke();
        });
    }
}
