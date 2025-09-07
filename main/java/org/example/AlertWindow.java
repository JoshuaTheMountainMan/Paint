package org.example;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class AlertWindow {


    @FXML
    void showHelpMenu(){
        Alert helpMenu = new Alert(Alert.AlertType.CONFIRMATION);
        helpMenu.setTitle("How to use");
        helpMenu.setHeaderText("How to use Pain(t)");
        helpMenu.setContentText("Instructions:\n I don't even know yet");
        Optional<ButtonType> result = helpMenu.showAndWait();
    }


    void showAboutMenu(){
        Alert aboutMenu = new Alert(Alert.AlertType.INFORMATION);
        aboutMenu.setTitle("About");
        aboutMenu.setHeaderText("About");
        aboutMenu.setContentText("This is a cheap version of Paint.\nStill in development.");
        Optional<ButtonType> result = aboutMenu.showAndWait();
    }


    void handleExit(FileMenu fileMenu){
        Alert handleExit = new Alert(Alert.AlertType.ERROR);
        handleExit.setTitle("Unsaved Changes");
        handleExit.setHeaderText("Do you want to save your work before exiting?");
        handleExit.setContentText("If you exit without saving, your changes will be lost.");

        ButtonType saveButton = new ButtonType("Save");
        ButtonType noSaveButton = new ButtonType("Exit without saving");
        ButtonType cancelButton = new ButtonType("Cancel");

        handleExit.getButtonTypes().setAll(saveButton,noSaveButton,cancelButton); //Adds created buttons to the new alert window

        handleExit.showAndWait().ifPresent(response -> {
            if (response == saveButton){
                fileMenu.onSaveAs();
                Platform.exit();
            } else if (response == noSaveButton){
                Platform.exit();
            }
        });


    }


}
