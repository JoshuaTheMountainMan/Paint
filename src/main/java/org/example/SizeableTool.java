package org.example;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

public class SizeableTool implements Tools{
    private double size;

    /**
     * Installs 3 main objects to any Sizeable tool inherited.
     * @param canvas Represents the editable part of the window.
     * @param graphics I don't really know but im too tired to think.
     * @param colorPicker Object that allows the object to be affected by the selected color
     */
    @Override
    public void install(Canvas canvas, GraphicsContext graphics, ColorPicker colorPicker) {
        graphics.setStroke(colorPicker.getValue());
        graphics.setLineWidth(getSize());
    }

    public double getSize(){return size;}
    public void setSize(double size){
        this.size = size;
    }




}


