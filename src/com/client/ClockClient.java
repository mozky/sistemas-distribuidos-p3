package com.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ClockClient extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/client/main.fxml"));

        loader.setController(new Controller());

        primaryStage.setTitle("P4 Sistemas Distribuidos");
        primaryStage.setScene(new Scene(loader.load(), 300, 150));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}