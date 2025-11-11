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
        primaryStage.setScene(new Scene(fxmlLoader.load(), 1000, 1000));

        // Ограничиваем размеры окна ДО показа
        primaryStage.setMinHeight(1000);  // минимальная высота
        primaryStage.setMaxHeight(1000); // максимальная высота
        primaryStage.setMinWidth(800);   // ограничить ширину
        primaryStage.setMaxWidth(1200);

        primaryStage.show();


        primaryStage.widthProperty().addListener((obs, oldVal, newVal) ->
                System.out.println("Ширина окна: " + newVal.intValue())
        );

        primaryStage.heightProperty().addListener((obs, oldVal, newVal) ->
                System.out.println("Высота окна: " + newVal.intValue())
        );
    }



    public static void main(String[] args) {
        launch(args);
    }
}

//jpackage --type exe --input . --dest . --main-jar KMPJavaFX_jar.jar --main-class com.example.kmpjavafx.Main --module-path "C:\Users\User\.jdks\javafx-jmods-25.0.1" --add-modules javafx.controls,javafx.fxml --win-shortcut --win-menu
