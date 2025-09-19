package com.example.kmpjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StartPageController {

    @FXML private Button openPdfPageButton;

    @FXML
    private void initialize() {
        openPdfPageButton.setOnAction(event -> openPdfPage());
    }

    private void openPdfPage() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/kmpjavafx/pdfPage.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            Stage stage = (Stage) openPdfPageButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
