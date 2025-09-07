package org.example;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javax.imageio.ImageIO;
import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

    public class PainController {
        @FXML
        private Canvas canvas;
        private Stage stage;
        private AlertWindow windowAlert = new AlertWindow();
        @FXML
        public ColorPicker colorPicker;
        private FileMenu fileMenuController;
        @FXML
        private TextField brushSize;
        private CanvasControl canvasControl;
        @FXML
        private TextField imageWidth;
        @FXML
        private TextField imageHeight;

        @FXML
        private void initialize(){
            canvasControl = new CanvasControl(canvas);
        }


        public void setStage(Stage stage) {
            this.stage = stage;

            // Inicializar el controlador

            fileMenuController = new FileMenu(stage, canvas);
            stage.setOnCloseRequest(windowEvent -> {
                windowEvent.consume(); windowAlert.handleExit(fileMenuController);});
        }

        /*
        public void onResize(){
            double width = Double.parseDouble(imageWidth.getText());
            double height = Double.parseDouble(imageHeight.getText());
        }*/


        // Connecting menu buttons to the ones in controller

        public void onSave() { fileMenuController.onSave(); }
        public void onSaveAs() { fileMenuController.onSaveAs(); }
        public void onLoadImage() { fileMenuController.onLoadImage(); }
        public void onExit() { javafx.application.Platform.exit(); }

        public void onHelp(){ windowAlert.showHelpMenu();}
        public void onAbout(){ windowAlert.showAboutMenu();}

        public void handleExit(FileMenu fileMenuController){windowAlert.handleExit(fileMenuController);}

        public void onBrushSize(){canvasControl.setBrushSize(Double.parseDouble(brushSize.getText()));}
    }
