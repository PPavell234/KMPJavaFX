package com.example.kmpjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/com/example/kmpjavafx/startPage.fxml")
        );
        primaryStage.setTitle("Новая страница");
        primaryStage.setScene(new Scene(fxmlLoader.load(), 1200, 1100));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
