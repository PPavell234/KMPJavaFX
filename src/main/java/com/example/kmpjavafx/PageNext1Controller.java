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
            return; // можно показать Alert
        }

        KMP kmp = new KMP();
        List<Integer> positions = kmp.search(documentText, pattern);

        if (!positions.isEmpty()) {
            // ✅ Совпадения найдены → открываем следующую страницу
            openNextPage();
        } else {
            System.out.println("Совпадений не найдено");
        }
    }

    private void openNextPage() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/kmpjavafx/pageNext2.fxml")
            );
            Parent root = loader.load();

            PageNext2Controller controller = loader.getController();
            controller.setDocumentText(documentText, searchField.getText()); // ✅ передаём и текст, и шаблон

            Stage stage = (Stage) searchField.getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 1100));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
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
