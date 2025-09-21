package org.example;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

public class RectangleTool extends SizeableTool{
    private Point2D firstPoint;
    private double width;
    private double height;
    private Shapes shape;

    RectangleTool(){
        shape = Shapes.RECTANGLE;
    }

    RectangleTool(Shapes shape){
        this.shape = shape;
    }



    @Override
    public void install(Canvas canvas, GraphicsContext graphics, ColorPicker colorPicker) {

        canvas.setOnMouseClicked(mouseEvent -> {
            super.install(canvas,graphics,colorPicker);

            if(firstPoint == null){  //First click
                firstPoint = new Point2D(mouseEvent.getX(),mouseEvent.getY());
            } else {
               double[] tempX = {firstPoint.getX()};
               double[] tempY = {firstPoint.getY()};

               width = Math.abs(mouseEvent.getX() - firstPoint.getX());
               height = Math.abs(mouseEvent.getY() - firstPoint.getY());

              if (shape == Shapes.SQUARE || shape == Shapes.CIRCLE){
                  width = Math.min(width, height);
                  height = Math.min(width, height);
              }

                if (mouseEvent.getX() < firstPoint.getX())
                    tempX[0] = mouseEvent.getX();
                if (mouseEvent.getY() < firstPoint.getY())
                    tempY[0] = mouseEvent.getY();

                firstPoint = new Point2D(tempX[0], tempY[0]);

                if(shape == Shapes.RECTANGLE || shape == Shapes.SQUARE){
                    graphics.strokeRect(firstPoint.getX(), firstPoint.getY(), width, height);
                }else {
                    graphics.strokeOval(firstPoint.getX(), firstPoint.getY(), width, height);
                }

                firstPoint = null; //resets the first point to select new ones later
            }
        });

    }

}
