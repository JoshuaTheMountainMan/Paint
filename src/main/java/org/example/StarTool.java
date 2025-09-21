package org.example;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

public class StarTool extends SizeableTool{
    private Point2D centerPoint;  // Primer click (centro)
    private Point2D edgePoint; // segundo click (radio)



    @Override
    public void install(Canvas canvas, GraphicsContext graphics, ColorPicker colorPicker) {


        canvas.setOnMouseClicked(mouseEvent -> {
            if (centerPoint == null) {
                // Primer click: centro de la estrella
                centerPoint = new Point2D(mouseEvent.getX(), mouseEvent.getY());
            } else {
                // Segundo click: define radio y dibuja
                edgePoint = new Point2D(mouseEvent.getX(), mouseEvent.getY());

                double dx = edgePoint.getX() - centerPoint.getX();
                double dy = edgePoint.getY() - centerPoint.getY();
                double radius = Math.sqrt(dx * dx + dy * dy);

                drawStar(graphics, centerPoint.getX(), centerPoint.getY(), radius, 5, colorPicker); // estrella de 5 puntas

                // Reset para permitir una nueva estrella
                centerPoint = null;
                edgePoint = null;
            }
        });




    }

    /**
     * Takes different parameters and creates a star shape with them.
     * @param gc The graphical part of the program.
     * @param centerX Saves the position of the click which will become the center.
     * @param centerY Saves the position of the click which will become the center.
     * @param radius Sets the size of the shape in terms of the selected positions.
     * @param numPoints Number of lines that are generated per point.
     * @param colorPicker Allows the shape to be affected by color
     */
        private void drawStar(GraphicsContext gc ,double centerX, double centerY, double radius, int numPoints, ColorPicker colorPicker) {
            double[] xPoints = new double[numPoints * 2];
            double[] yPoints = new double[numPoints * 2];

            for (int i = 0; i < numPoints * 2; i++) {
                double angle = Math.PI / numPoints * i;
                double r = (i % 2 == 0) ? radius : radius / 2.5; // alterna radio grande y pequeño
                xPoints[i] = centerX + Math.cos(angle - Math.PI / 2) * r;
                yPoints[i] = centerY + Math.sin(angle - Math.PI / 2) * r;
            }
            gc.setStroke(colorPicker.getValue());
            gc.setLineWidth(getSize());
            gc.strokePolygon(xPoints, yPoints, numPoints * 2);
        }
}
