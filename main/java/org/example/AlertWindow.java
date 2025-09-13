package org.example;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.io.File;
import java.util.Optional;

public class AlertWindow {


    /**
     * This method creates an alert object and adds information and buttons to it.
     * Designed specifically for the Help Window in the Help Menu
     */
    @FXML
    void showHelpMenu(){
        Alert helpMenu = new Alert(Alert.AlertType.CONFIRMATION);
        helpMenu.setTitle("How to use");
        helpMenu.setHeaderText("How to use Pain(t)");
        helpMenu.setContentText("Instructions:\n I don't even know yet");
        Optional<ButtonType> result = helpMenu.showAndWait();
    }

    /**
     * This method creates an alert object and adds information and buttons to it.
     */
    void showAboutMenu(){
        Alert aboutMenu = new Alert(Alert.AlertType.INFORMATION);
        aboutMenu.setTitle("About");
        aboutMenu.setHeaderText("About");
        aboutMenu.setContentText("This is a cheap version of Paint.\nStill in development.");
        Optional<ButtonType> result = aboutMenu.showAndWait();
    }


    /**
     * This method is called every time that the exit button is clicked, to handle the exit before it is just closed and changes are lost.
     * @param fileMenu Object derived from FileMenu class that allows the method to verify if the file has been saved before.
     */
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
