package com.example.kmpjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class PageNext2Controller {

    @FXML
    private TextFlow textFlow;

    @FXML
    private TextField searchField;

    private String documentText = "";

    // Приходит из PageNext1Controller
    public void setDocumentText(String text, String pattern) {
        this.documentText = text;
        highlightMatches(pattern);
    }

    // Обработчик клика по иконке Send.png
    @FXML
    private void onSearchClicked(MouseEvent event) {
        String pattern = searchField.getText();
        highlightMatches(pattern);
    }

    // Подсветка совпадений
    private void highlightMatches(String pattern) {
        textFlow.getChildren().clear();

        if (pattern == null || pattern.isEmpty() || documentText == null || documentText.isEmpty()) {
            textFlow.getChildren().add(new Text(documentText != null ? documentText : ""));
            return;
        }

        KMP kmp = new KMP();
        List<Integer> positions = kmp.search(documentText, pattern);

        if (positions.isEmpty()) {
            textFlow.getChildren().add(new Text("Совпадений не найдено"));
            return;
        }

        int patternLength = pattern.length();
        int lastIndex = 0;

        for (int pos : positions) {
            // обычный текст до совпадения
            if (pos > lastIndex) {
                textFlow.getChildren().add(new Text(documentText.substring(lastIndex, pos)));
            }

            // совпадение → используем Label для подсветки фоном
            javafx.scene.control.Label highlighted = new javafx.scene.control.Label(documentText.substring(pos, pos + patternLength));
            highlighted.setStyle("-fx-background-color: yellow; -fx-text-fill: black;");

            textFlow.getChildren().add(highlighted);

            lastIndex = pos + patternLength;
        }

        // остаток текста
        if (lastIndex < documentText.length()) {
            textFlow.getChildren().add(new Text(documentText.substring(lastIndex)));
        }
    }

    // Назад (если нужно вернуться на предыдущую сцену)
    @FXML
    private void onBackClicked(MouseEvent event) {
        System.out.println("Назад нажато — вернись на PageNext1 или стартовую страницу");
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


}
