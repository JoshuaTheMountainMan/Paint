package org.example;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class DropperTool implements Tools{
    @Override
    public void install(Canvas canvas, GraphicsContext graphics, ColorPicker colorPicker) {
        canvas.setOnMouseClicked(mouseEvent -> {
            Image snapshot = canvas.snapshot(null,null);
            PixelReader pixelReader = snapshot.getPixelReader();

            int x = (int) mouseEvent.getX();
            int y = (int) mouseEvent.getY();

            if (x < snapshot.getWidth() && y < snapshot.getHeight()){
                Color color = pixelReader.getColor(x,y);
                colorPicker.setValue(color);
                graphics.setStroke(color);
            }

            canvas.setOnMouseClicked(null);

        });
    }
}
