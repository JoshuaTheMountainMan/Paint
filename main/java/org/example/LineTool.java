package org.example;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

public class LineTool extends SizeableTool{
    private Point2D firstPoint;
    private Point2D secondPoint;

    @Override
    public void install(Canvas canvas, GraphicsContext graphics, ColorPicker colorPicker) {

        canvas.setOnMouseClicked(mouseEvent -> {
            super.install(canvas,graphics,colorPicker);

            if(firstPoint == null){  //First click
                firstPoint = new Point2D(mouseEvent.getX(),mouseEvent.getY());
            } else {
                secondPoint = new Point2D(mouseEvent.getX(),mouseEvent.getY());
                graphics.strokeLine(firstPoint.getX(), firstPoint.getY(), secondPoint.getX(), secondPoint.getY());

                firstPoint = null; //resets the first point to select new ones later
            }
        });

    }


}
