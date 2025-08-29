package org.example;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import java.awt.*;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javax.imageio.ImageIO;
import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PainController {

    @FXML
    private Canvas canvas;
    private  File loadedImage;
    private Stage stage;

    public void setStage(Stage stage){
        this.stage = stage;
    }


    public void onSave(){
        try{
            Image snapshot = canvas.snapshot(null,null);

            ImageIO.write(SwingFXUtils.fromFXImage(snapshot,null), "png",loadedImage);
        }
        catch (Exception e){
            System.out.println("Failed to save image:" + e);
        }

    }

    public void onSaveAs(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showSaveDialog(stage);

        try{
            Image snapshot = canvas.snapshot(null,null);

            ImageIO.write(SwingFXUtils.fromFXImage(snapshot,null), "png", file);
        }
        catch (Exception e){
            System.out.println("Failed to save image:" + e);
        }
    }


    public void onExit(){
        Platform.exit();

    }


    public void onLoadImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        loadedImage = fileChooser.showOpenDialog(stage);

        if (loadedImage != null){
            Image image = new Image(loadedImage.toURI().toString());
            canvas.setHeight(image.getHeight());
            canvas.setWidth(image.getWidth());

            GraphicsContext gc = canvas.getGraphicsContext2D();

            gc.clearRect(0,0,canvas.getWidth(), canvas.getHeight());
            gc.drawImage(image,0,0);
        }

    }
}
