package org.example.magame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("game-Frame.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //string might
        Image logo = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/dog.png")));
        stage.setTitle("Maze Game!!!");
        stage.getIcons().add(logo);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}