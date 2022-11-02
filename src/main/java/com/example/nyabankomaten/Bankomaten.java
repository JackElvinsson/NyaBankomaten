package com.example.nyabankomaten;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Bankomaten extends Application {
    @Override
    public void start(Stage stage) {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(Bankomaten.class.getResource("bankomaten.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Administrator Panel");
            stage.setScene(scene);
            stage.show();

        }catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}