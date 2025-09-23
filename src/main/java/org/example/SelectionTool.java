package org.example;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class SelectionTool {
    private double startX;
    private double startY;
    private Rectangle selection = new Rectangle();

    public SelectionTool() {
        selection.setStroke(Color.BLUE);
        selection.setFill(Color.color(0, 0, 1, 0.12));
        selection.setVisible(false);
    }

    public void install(Pane overlayPane, ImageView mainImageView, LayerManager layerManager, CanvasHistory history) {
        if (!overlayPane.getChildren().contains(selection)) overlayPane.getChildren().add(selection);

        overlayPane.setOnMousePressed(e -> {
            Point2D p = overlayPane.sceneToLocal(e.getSceneX(), e.getSceneY());
            startX = p.getX();
            startY = p.getY();
            selection.setX(startX);
            selection.setY(startY);
            selection.setWidth(0);
            selection.setHeight(0);
            selection.setVisible(true);
            e.consume();
        });

        overlayPane.setOnMouseDragged(e -> {
            Point2D p = overlayPane.sceneToLocal(e.getSceneX(), e.getSceneY());
            double x = Math.min(startX, p.getX());
            double y = Math.min(startY, p.getY());
            double w = Math.abs(p.getX() - startX);
            double h = Math.abs(p.getY() - startY);
            selection.setX(x); selection.setY(y); selection.setWidth(w); selection.setHeight(h);
            e.consume();
        });

        overlayPane.setOnMouseReleased(e -> {
            selection.setVisible(false);
            if (selection.getWidth() > 1 && selection.getHeight() > 1 && mainImageView.getImage() != null) {
                Rectangle2D pixRect = selectionToImagePixelRect(selection, mainImageView, overlayPane);
                PixelReader reader = mainImageView.getImage().getPixelReader();
                if (reader != null) {
                    WritableImage cropped = new WritableImage(reader,
                            (int) pixRect.getMinX(), (int) pixRect.getMinY(),
                            (int) pixRect.getWidth(), (int) pixRect.getHeight());

                    // Add piece scaled to selection size & position in imageLayer
                    ImageView piece = layerManager.addPiece(cropped, selection.getX(), selection.getY());
                    // Hacer arrastrable
                    layerManager.makeNodeDraggable(piece, history);
                    // Guardar estado
                    if (history != null) history.saveState();
                }
            }
            e.consume();
        });
    }

    private Rectangle2D selectionToImagePixelRect(Rectangle sel, ImageView imageView, Pane overlayPane){
        Point2D topLeftScene = overlayPane.localToScene(sel.getX(), sel.getY());
        Point2D bottomRightScene = overlayPane.localToScene(sel.getX() + sel.getWidth(), sel.getY() + sel.getHeight());

        Point2D topLeftImageLocal = imageView.sceneToLocal(topLeftScene);
        Point2D bottomRightImageLocal = imageView.sceneToLocal(bottomRightScene);

        double dispW = imageView.getBoundsInLocal().getWidth();
        double dispH = imageView.getBoundsInLocal().getHeight();
        double imgW = imageView.getImage().getWidth();
        double imgH = imageView.getImage().getHeight();

        double x = clamp(topLeftImageLocal.getX(), 0, dispW);
        double y = clamp(topLeftImageLocal.getY(), 0, dispH);
        double x2 = clamp(bottomRightImageLocal.getX(), 0, dispW);
        double y2 = clamp(bottomRightImageLocal.getY(), 0, dispH);

        int px = (int) Math.round(x * imgW / dispW);
        int py = (int) Math.round(y * imgH / dispH);
        int pw = (int) Math.max(1, Math.round((x2 - x) * imgW / dispW));
        int ph = (int) Math.max(1, Math.round((y2 - y) * imgH / dispH));

        return new Rectangle2D(px, py, pw, ph);
    }

    private double clamp(double v, double a, double b) { return Math.max(a, Math.min(b, v)); }

}

