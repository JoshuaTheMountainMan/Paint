package org.example;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;

public class MoveImageTool implements Tools{
    private double offsetX, offsetY;
    private double imageX = 0;
    private double imageY = 0;
    private Image image;

    public MoveImageTool(Image image){
        this.image = image;
    }

    
    @Override
    public void install(Canvas canvas, GraphicsContext graphics, ColorPicker colorPicker) {
        canvas.setOnMousePressed(e -> {
            offsetX = e.getX() - imageX;
            offsetY = e.getY()- imageY;
        });

        canvas.setOnMouseDragged(e -> {
            imageX = e.getX() - offsetX;
            imageY = e.getY() - offsetY;

            graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            graphics.drawImage(image, imageX, imageY);
        });

        canvas.setOnMouseReleased(e -> {
            imageX = e.getX();
            imageY = e.getY();
        });

    }
}
