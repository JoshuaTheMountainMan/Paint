package org.example;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

public class EraserTool extends SizeableTool{
    double lastX;
    double lastY;

    public EraserTool(){setSize(1);}
    public EraserTool(double size){setSize(size);}


    @Override
    public void install(Canvas canvas, GraphicsContext graphics, ColorPicker colorPicker) {
        canvas.setOnMousePressed(mouseEvent -> {
            lastX = mouseEvent.getX();
            lastY = mouseEvent.getY();

            graphics.setStroke(Color.valueOf("f4f4f4"));
            graphics.setLineWidth(getSize());
        });

        canvas.setOnMouseDragged(mouseEvent -> {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();

            graphics.strokeLine(lastX, lastY, x,y);
            lastX = x;
            lastY = y;
        });
    }
}
