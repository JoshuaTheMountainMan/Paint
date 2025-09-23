package org.example;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public class LayerManager {
    private ImageView imageView;
    private Pane imageLayer;
    private Pane shapesLayer;
    private Pane overlayPane;
    private Canvas drawCanvas;
    private List<ImageView> pieces = new ArrayList<>();

    public LayerManager(ImageView imageView, Pane imageLayer, Pane shapesLayer, Pane overlayPane, Canvas drawCanvas){
        this.imageView = imageView;
        this.imageLayer = imageLayer;
        this.shapesLayer = shapesLayer;
        this.overlayPane = overlayPane;
        this.drawCanvas = drawCanvas;
    }

    public void setMainImage(Image image) {
        imageView.setImage(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(image.getWidth());
        imageView.setFitHeight(image.getHeight());

        imageLayer.setLayoutX(0);
        imageView.setLayoutY(0);

        if (!imageLayer.getChildren().contains(imageView)) {
            imageLayer.getChildren().add(imageView);
        }
    }

    public ImageView addPiece(WritableImage pieceImg, double layoutX, double layoutY) {
        ImageView piece = new ImageView(pieceImg);
        piece.setLayoutX(layoutX);
        piece.setLayoutY(layoutY);
        piece.setPreserveRatio(false);
        imageLayer.getChildren().add(piece);
        pieces.add(piece);
        return piece;
    }

    public void removePiece(ImageView piece) {
        pieces.remove(piece);
        imageLayer.getChildren().remove(piece);
    }

    public void makeNodeDraggable(Node node, CanvasHistory history) {
        final Point2D[] offset = new Point2D[1];

        node.setOnMousePressed(ev -> {
            node.toFront();
            Point2D p = imageLayer.sceneToLocal(ev.getSceneX(), ev.getSceneY());
            offset[0] = new Point2D(p.getX() - node.getLayoutX(), p.getY() - node.getLayoutY());
            ev.consume();
        });

        node.setOnMouseDragged(ev -> {
            Point2D p = imageLayer.sceneToLocal(ev.getSceneX(), ev.getSceneY());
            node.setLayoutX(p.getX() - offset[0].getX());
            node.setLayoutY(p.getY() - offset[0].getY());
            ev.consume();
        });

        node.setOnMouseReleased(ev -> {
            // guardamos snapshot para undo/redo (nota: snapshot aplana el resultado)
            if (history != null) history.saveState();
            ev.consume();
        });
    }

    public Pane getImageLayer() { return imageLayer; }
    public Pane getShapesLayer() { return shapesLayer; }
    public Pane getOverlayPane() { return overlayPane; }
    public ImageView getImageView() { return imageView; }
    public Canvas getDrawCanvas() { return drawCanvas; }
}






