package com.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClockServer extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/server/main.fxml"));

        loader.setController(new Controller());

        primaryStage.setTitle("P4 Sistemas Distribuidos");
        primaryStage.setScene(new Scene(loader.load(), 300, 250));

        primaryStage.show();
    }

    public static void main(String[] args) {
        //Creating User Interface
        launch(args);
    }
}
