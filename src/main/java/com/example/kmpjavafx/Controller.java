package com.example.kmpjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

import static org.apache.pdfbox.pdmodel.PDDocument.*;

public class Controller {

    @FXML
    private TextField patternField;

    @FXML
    private Button searchButton;

    @FXML
    private Button loadPdfButton;

    @FXML
    private VBox textContainer;

    private String currentText = "";

    @FXML
    private void initialize() {
        // Загрузка PDF
        loadPdfButton.setOnAction(event -> loadPdf());

        // Поиск совпадений
        searchButton.setOnAction(event -> {
            textContainer.getChildren().clear();
            String pattern = patternField.getText();
            if (currentText.isEmpty() || pattern.isEmpty()) {
                textContainer.getChildren().add(new Text("Загрузите PDF и введите шаблон.\n"));
                return;
            }
            highlightMatches(currentText, pattern);
        });
    }

    private void loadPdf() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try (PDDocument document = PDDocument.load(file)) {   // ✅ в PDFBox 2.0.34 это работает
                PDFTextStripper stripper = new PDFTextStripper();
                currentText = stripper.getText(document);

                textContainer.getChildren().clear();
                textContainer.getChildren().add(new Text(
                        "PDF загружен. Длина текста: " + currentText.length() + " символов.\n"
                ));
            } catch (IOException e) {
                e.printStackTrace();
                textContainer.getChildren().clear();
                textContainer.getChildren().add(new Text("Ошибка загрузки PDF.\n"));
            }
        }
    }


    private void highlightMatches(String text, String pattern) {
        KMP kmp = new KMP();
        List<Integer> positions = kmp.search(text, pattern);

        TextFlow flow = new TextFlow();
        int lastIndex = 0;

        for (int pos : positions) {
            // Текст до совпадения
            if (pos > lastIndex) {
                flow.getChildren().add(new Text(text.substring(lastIndex, pos)));
            }
            // Совпадение (красным)
            Text matchText = new Text(text.substring(pos, pos + pattern.length()));
            matchText.setStyle("-fx-fill: red; -fx-font-weight: bold;");
            flow.getChildren().add(matchText);

            lastIndex = pos + pattern.length();
        }

        // Остаток текста
        if (lastIndex < text.length()) {
            flow.getChildren().add(new Text(text.substring(lastIndex)));
        }

        textContainer.getChildren().clear();
        textContainer.getChildren().add(flow);
    }
}
