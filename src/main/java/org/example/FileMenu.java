package org.example;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;


public class FileMenu {

    @FXML
    private Canvas canvas;
    private File loadedImage;
    private Stage stage;
    public boolean saved = false;


    //Constructor: Recibe canvas y stage para trabajar

    /**
     * Receives the stage and canvas object to work.
     * @param stage Where the main window is shown.
     * @param canvas Object of the "workable" surface of the window
     */

    public FileMenu(Stage stage, Canvas canvas) {
        this.stage = stage;
        this.canvas = canvas;
    }



    //SAVE

    /**
     * Method that tells the program what to do when Save Button is pressed.
     * Creates a snapshot of everything on the screen.
     */
    public void onSave() {

        //If file not saved previously, it calls Save as
        if (loadedImage == null) {
            onSaveAs();
            return;
        }

        try {
            Image snapshot = canvas.snapshot(null, null);

            String fileName = loadedImage.getName().toLowerCase();
            String format = "png";  //sets default format

            if (fileName.endsWith("jpg") || fileName.endsWith("jpeg")) {
                format = "jpg";
            } else if (fileName.endsWith("bmp")) {
                format = "bmp";
            }

            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), format, loadedImage);
        } catch (Exception e) {
            System.out.println("Failed to save image:" + e);
        }


    }


//SAVE AS

    /**
     * Creates a FileChooser object that is used to allow the program to access the storage of the user letting it save files in the PC.
     * Manages file types.
     */
    public void onSaveAs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                new FileChooser.ExtensionFilter("JPG Files", "*.jpg"),
                new FileChooser.ExtensionFilter("BMP Files", "*.bmp")
        );
        File file = fileChooser.showSaveDialog(stage);


        //TURN INTO A FUNCTION, AVOID REPEATED CODE


        if (file != null) {
            try {
                Image snapshot = canvas.snapshot(null, null);

                // Detect the extension of the image

                String fileName = file.getName().toLowerCase();
                String format = "png"; // Sets default format again

                if (fileName.endsWith(".jpeg") || fileName.endsWith(".jpg")) {
                    format = "jpg";
                } else if (fileName.endsWith(".bmp")) {
                    format = "bmp";
                }

                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), format, file);
                isSaved();
            } catch (Exception e) {
                System.out.println("Failed to save image:" + e);
            }

        }


    }

    public boolean isSaved(){
        return saved = true;
    }

//Temp function
    public File getImage(){
        return loadedImage;
    }



    //LOAD IMAGE

    /**
     * Opens the file explorer by using FileChooser object.
     * Filters saved files by type.
     */
    public Image onLoadImage() {
        FileChooser fileChooser = new FileChooser(); //Max - you can create a fileChooser object as a private field at the top of your class and initialize/add extension filters for
                                                     // it in your constructor, that way you won't have to do that twice in saveAs/loadImage
        fileChooser.setTitle("Select Image");

        //Lets the user choose what format of picture to select

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                new FileChooser.ExtensionFilter("JPG Files", "*.jpg"),
                new FileChooser.ExtensionFilter("BMP Files", "*.bmp")
        );
        loadedImage = fileChooser.showOpenDialog(stage);

        //MOVE TO CANVAS CONTROL (Not a file feature)
        if (loadedImage != null) {
            Image image = new Image(loadedImage.toURI().toString());
           // canvas.setHeight(image.getHeight());
            //canvas.setWidth(image.getWidth());

            GraphicsContext gc = canvas.getGraphicsContext2D();

            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc.drawImage(image, 0, 0);

            return image;
        };
        return null;
    }

}
