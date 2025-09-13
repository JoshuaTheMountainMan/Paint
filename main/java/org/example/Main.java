package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{
    @Override

    public void start(Stage stage) throws Exception {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pruebapaint.fxml"));
        Scene scene = new Scene(loader.load());
        org.example.PainController controller = loader.getController();
        controller.setStage(stage);

        stage.setScene(scene);


        stage.show();
    }
    public static void main(String[] args) {

        launch(args);

    }


    /*
    Q: Why do Java developers wear glasses?
    A: Because they don't C#
     */


}