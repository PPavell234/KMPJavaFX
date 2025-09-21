package com.example.kmpjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class PageNext1Controller {
    @FXML
    private TextField searchField;

    private String documentText = "";

    public void setDocumentText(String text) {
        this.documentText = text;
    }

    @FXML
    private void onSearch() {
        String pattern = searchField.getText();

        if (pattern == null || pattern.isEmpty()) {
            showMessage("Введите текст для поиска");
            return;
        }

        if (documentText == null || documentText.isEmpty()) {
            showMessage("Документ пустой или не загружен");
            return;
        }

        KMP kmp = new KMP();
        List<Integer> positions = kmp.search(documentText, pattern);

        if (positions.isEmpty()) {
            showMessage("Совпадений не найдено");
        } else {
            showMessage("Совпадения найдены (" + positions.size() + ")");
        }
    }

    @FXML
    private void openHelloView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/kmpjavafx/infoPage.fxml")
            );
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void BackIsInfoPage(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/kmpjavafx/startPage.fxml")
            );
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showMessage(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Результат поиска");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }


}
