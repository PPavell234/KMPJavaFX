package com.example.kmpjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;

public class PdfPageController {

    @FXML private Button loadPdfButton;

    @FXML
    private void initialize() {
        loadPdfButton.setOnAction(event -> loadPdf());
    }

    private void loadPdf() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF files", "*.pdf"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try (PDDocument document = PDDocument.load(file)) {
                PDFTextStripper stripper = new PDFTextStripper();
                String text = stripper.getText(document);
                System.out.println("PDF загружен. Длина текста: " + text.length());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}