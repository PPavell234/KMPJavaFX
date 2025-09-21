package com.example.kmpjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

    // вызывается из PageNext1Controller
    public void setDocumentText(String text, String pattern) {
        this.documentText = text;
        highlightMatches(pattern);
    }

    // клик по иконке Send на этой странице
    @FXML
    private void onSearchClicked(MouseEvent event) {
        String pattern = searchField.getText();
        highlightMatches(pattern);
    }

    private void highlightMatches(String pattern) {
        textFlow.getChildren().clear();

        if (pattern == null || pattern.isEmpty() || documentText == null || documentText.isEmpty()) {
            Text all = new Text(documentText != null ? documentText : "");
            all.setFill(Color.WHITE); // белый текст
            textFlow.getChildren().add(all);
            return;
        }

        KMP kmp = new KMP();
        List<Integer> positions = kmp.search(documentText, pattern);

        if (positions.isEmpty()) {
            Text notFound = new Text("Совпадений не найдено");
            notFound.setFill(Color.WHITE); // белый
            textFlow.getChildren().add(notFound);
            return;
        }

        int patternLength = pattern.length();
        int lastIndex = 0;

        for (int pos : positions) {
            // обычный текст
            if (pos > lastIndex) {
                Text normalText = new Text(documentText.substring(lastIndex, pos));
                normalText.setFill(Color.WHITE); // белый текст
                textFlow.getChildren().add(normalText);
            }

            // совпадение — подсветка жёлтым
            Label highlighted = new Label(documentText.substring(pos, pos + patternLength));
            highlighted.setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
            textFlow.getChildren().add(highlighted);

            lastIndex = pos + patternLength;
        }

        // остаток
        if (lastIndex < documentText.length()) {
            Text tail = new Text(documentText.substring(lastIndex));
            tail.setFill(Color.WHITE); // белый текст
            textFlow.getChildren().add(tail);
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


}
