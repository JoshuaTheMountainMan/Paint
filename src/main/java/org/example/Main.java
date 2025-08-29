package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application{
    @Override

    public void start(Stage stage) throws Exception {

        //stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("paint.fxml"))));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("paint.fxml"));
        Scene scene = new Scene(loader.load());
        PainController controler = loader.getController();
        controler.setStage(stage);

        stage.setScene(scene);


        stage.show();
    }
    public static void main(String[] args) {

        launch(args);

    }


}