package com.example.kmpjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Controller {

    @FXML private TextField patternField;
    @FXML private Button searchButton;
    @FXML private Button loadPdfButton;
    @FXML private VBox textContainer;

    @FXML private Label matchCountLabel;
    @FXML private Button prevButton;
    @FXML private Button nextButton;

    private String currentText = "";
    private List<Integer> positions;
    private int currentMatchIndex = -1;

    @FXML
    private void initialize() {
        loadPdfButton.setOnAction(event -> loadPdf());
        searchButton.setOnAction(event -> searchAndHighlight());

        prevButton.setOnAction(event -> moveToMatch(-1));
        nextButton.setOnAction(event -> moveToMatch(1));
    }

    private void loadPdf() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try (PDDocument document = PDDocument.load(file)) {
                PDFTextStripper stripper = new PDFTextStripper();
                currentText = stripper.getText(document);
                textContainer.getChildren().clear();
                textContainer.getChildren().add(new Text("PDF загружен. Длина текста: " + currentText.length() + " символов.\n"));
            } catch (IOException e) {
                e.printStackTrace();
                textContainer.getChildren().clear();
                textContainer.getChildren().add(new Text("Ошибка загрузки PDF.\n"));
            }
        }
    }

    private void searchAndHighlight() {
        textContainer.getChildren().clear();
        String pattern = patternField.getText();
        if (currentText.isEmpty() || pattern.isEmpty()) {
            textContainer.getChildren().add(new Text("Загрузите PDF и введите шаблон.\n"));
            return;
        }

        KMP kmp = new KMP();
        positions = kmp.search(currentText, pattern);
        currentMatchIndex = positions.isEmpty() ? -1 : 0;

        matchCountLabel.setText(String.valueOf(positions.size()));
        highlightMatches();
    }

    private void highlightMatches() {
        if (positions == null) return;
        String pattern = patternField.getText();

        TextFlow flow = new TextFlow();
        int lastIndex = 0;

        for (int i = 0; i < positions.size(); i++) {
            int pos = positions.get(i);

            if (pos > lastIndex) {
                flow.getChildren().add(new Text(currentText.substring(lastIndex, pos)));
            }

            Text matchText = new Text(currentText.substring(pos, pos + pattern.length()));
            if (i == currentMatchIndex) {
                matchText.setStyle("-fx-fill: yellow; -fx-font-weight: bold;"); // активное совпадение
            } else {
                matchText.setStyle("-fx-fill: red; -fx-font-weight: bold;");    // остальные совпадения
            }
            flow.getChildren().add(matchText);

            lastIndex = pos + pattern.length();
        }

        if (lastIndex < currentText.length()) {
            flow.getChildren().add(new Text(currentText.substring(lastIndex)));
        }

        textContainer.getChildren().clear();
        textContainer.getChildren().add(flow);
    }

    private void moveToMatch(int direction) {
        if (positions == null || positions.isEmpty()) return;

        currentMatchIndex += direction;
        if (currentMatchIndex < 0) currentMatchIndex = positions.size() - 1;
        if (currentMatchIndex >= positions.size()) currentMatchIndex = 0;

        highlightMatches();
    }
}
