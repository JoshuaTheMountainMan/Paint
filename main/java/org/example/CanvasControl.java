package org.example;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.StrokeLineCap;

public class CanvasControl {
    private Canvas canvas;
    private GraphicsContext graphCon;
    private double brushSize;



    public CanvasControl(Canvas canvas){
        this.canvas = canvas;
        this.graphCon = canvas.getGraphicsContext2D();
        graphCon.setLineCap(StrokeLineCap.ROUND);
       // setMouseEvents();

    }


    public GraphicsContext getGraphCon(){
        return graphCon;
    }

}
