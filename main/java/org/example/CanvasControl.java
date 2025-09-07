package org.example;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.StrokeLineCap;

public class CanvasControl {
    private Canvas canvas;
    private GraphicsContext graphCon;
    private boolean modified = false;
    private double brushSize;


    public void drawImage(){
        FileMenu loadedImage;


    }

    public CanvasControl(Canvas canvas){
        this.canvas = canvas;
        this.graphCon = canvas.getGraphicsContext2D();
        graphCon.setLineCap(StrokeLineCap.ROUND);
        setMouseEvents();

    }

    private void setMouseEvents(){
        final double[] lastX = new double[1];
        final double[] lastY = new double[1];

        //Start location before dragging
        canvas.setOnMousePressed(mouseEvent -> {
            lastX[0] = mouseEvent.getX();
            lastY[0] = mouseEvent.getY();
        });

        canvas.setOnMouseDragged(mouseEvent -> {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();

            graphCon.strokeLine(lastX[0], lastY[0], x,y);
            lastX[0] = x;
            lastY[0] = y;
        });
    }

    public void setBrushSize(double brushSize){
        this.brushSize = brushSize;
        graphCon.setLineWidth(brushSize);
    }

}
