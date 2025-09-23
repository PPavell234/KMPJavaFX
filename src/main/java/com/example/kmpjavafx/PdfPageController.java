package com.example.kmpjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PdfPageController {

    @FXML
    private ImageView dropTarget; // картинка, на которую перетаскиваем PDF

    @FXML
    private Button loadPdfButton;

    @FXML
    private void initialize() {
        // кнопка "Открыть PDF"
        loadPdfButton.setOnAction(event -> loadPdf());

        // DragOver — разрешаем перетаскивание
        dropTarget.setOnDragOver(event -> {
            if (event.getGestureSource() != dropTarget &&
                    event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        // Drop — загружаем PDF
        dropTarget.setOnDragDropped(this::handleFileDropped);
    }

    @FXML
    private void loadPdf() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF files", "*.pdf")
        );
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            openPdfAndGoNext(file);
        }
    }

    private void handleFileDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;

        if (db.hasFiles()) {
            File file = db.getFiles().get(0);
            if (file.getName().toLowerCase().endsWith(".pdf")) {
                openPdfAndGoNext(file);
                success = true;
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }

    private void openPdfAndGoNext(File file) {
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            System.out.println("PDF загружен. Длина текста: " + text.length());

            // Загружаем новую страницу
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/kmpjavafx/pageNext1.fxml")
            );
            Parent root = loader.load();

            // передаём текст в контроллер
            PageNext1Controller controller = loader.getController();
            controller.setDocumentText(text);

            // меняем сцену
            Stage stage = (Stage) loadPdfButton.getScene().getWindow();
            stage.setScene(new Scene(root, 1200, 1100));
            stage.show();

        } catch (Exception e) {
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
}
