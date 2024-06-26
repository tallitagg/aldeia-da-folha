package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("adm-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Aldeia da Folha");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}