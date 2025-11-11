package com.example.kmpjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/com/example/kmpjavafx/pageNext2.fxml")
        );
        primaryStage.setTitle("Курсовая работа Павел");
        primaryStage.setScene(new Scene(fxmlLoader.load(), 1200, 1100));
        primaryStage.show();


        // Добавляем слушателей для изменения размеров окна
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Ширина окна: " + newVal.intValue());
        });

        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Высота окна: " + newVal.intValue());
        });
    }



    public static void main(String[] args) {
        launch(args);
    }
}

//jpackage --type exe --input . --dest . --main-jar KMPJavaFX_jar.jar --main-class com.example.kmpjavafx.Main --module-path "C:\Users\User\.jdks\javafx-jmods-25.0.1" --add-modules javafx.controls,javafx.fxml --win-shortcut --win-menu
