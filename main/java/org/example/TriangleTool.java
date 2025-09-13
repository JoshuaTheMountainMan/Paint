package org.example;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;


public class TriangleTool extends SizeableTool{
    private Point2D firstpoint;
    private Point2D secondPoint;


    @Override
    public void install(Canvas canvas, GraphicsContext graphics, ColorPicker colorPicker) {
        canvas.setOnMouseClicked(mouseEvent -> {
        super.install(canvas, graphics, colorPicker);

        if (firstpoint == null){
            firstpoint = new Point2D(mouseEvent.getX(), mouseEvent.getY());
        } else if (secondPoint == null){
            secondPoint = new Point2D(mouseEvent.getX(),mouseEvent.getY());
        } else {
            double[] xPoints = {firstpoint.getX(), secondPoint.getX(), mouseEvent.getX()};  //stores x values
            double[] yPoints = {firstpoint.getY(), secondPoint.getY(), mouseEvent.getY()}; //stores y values

            graphics.strokePolygon(xPoints, yPoints, 3); //3 is the number of points

            firstpoint = secondPoint = null;
        }

        });


    }
}
