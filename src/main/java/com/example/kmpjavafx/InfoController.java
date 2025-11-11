package com.example.kmpjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;

public class InfoController {

    @FXML
    private TextFlow textFlow;

    /**
     * Возврат на стартовую страницу
     */
    @FXML
    private void BackIsInfoPage(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/kmpjavafx/startPage.fxml")
            );
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));

            // Добавляем слушатели изменения размеров
            logStageSize(stage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Копирование текста из TextFlow в буфер обмена
     */
    @FXML
    private void CopyTextBuffer(MouseEvent event) {
        StringBuilder sb = new StringBuilder();
        for (Node node : textFlow.getChildren()) {
            if (node instanceof Text) {
                sb.append(((Text) node).getText());
            }
        }

        ClipboardContent content = new ClipboardContent();
        content.putString(sb.toString());
        Clipboard.getSystemClipboard().setContent(content);

        System.out.println("Текст скопирован в буфер!");
    }

    /**
     * Добавляет слушатели для вывода текущих размеров окна в консоль
     */
    private void logStageSize(Stage stage) {
        stage.widthProperty().addListener((obs, oldVal, newVal) ->
                System.out.println("Ширина окна: " + newVal.intValue())
        );
        stage.heightProperty().addListener((obs, oldVal, newVal) ->
                System.out.println("Высота окна: " + newVal.intValue())
        );
    }
}
