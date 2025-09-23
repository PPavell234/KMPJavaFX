package com.example.kmpjavafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PageNext2Controller {

    @FXML
    private javafx.scene.image.ImageView successImage;

    @FXML
    private javafx.scene.control.Label matchesLabel;

    @FXML
    private TextFlow textFlow;

    @FXML
    private TextField searchField;

    @FXML
    private ScrollPane scrollPane; // ✅ добавили для автопрокрутки

    private String documentText = "";

    private List<Integer> positions = new ArrayList<>();
    private int currentMatchIndex = -1;

    // вызывается из PageNext1Controller
    public void setDocumentText(String text, String pattern) {
        this.documentText = text;
        findMatches(pattern);
    }

    // поиск по кнопке Send
    @FXML
    private void onSearchClicked(MouseEvent event) {
        String pattern = searchField.getText();
        findMatches(pattern);
    }

    private void findMatches(String pattern) {
        textFlow.getChildren().clear();
        positions.clear();
        currentMatchIndex = -1;

        if (pattern == null || pattern.isEmpty() || documentText == null || documentText.isEmpty()) {
            Text all = new Text(documentText != null ? documentText : "");
            all.setFill(Color.WHITE);
            textFlow.getChildren().add(all);

            if (matchesLabel != null) {
                matchesLabel.setText("Количество совпадений: 0");
            }
            if (successImage != null) {
                successImage.setVisible(true); // показать картинку
            }
            return;
        }

        KMP kmp = new KMP();
        positions = kmp.search(documentText, pattern);

        if (positions.isEmpty()) {
            Text notFound = new Text("Совпадений не найдено");
            notFound.setFill(Color.WHITE);
            textFlow.getChildren().add(notFound);

            if (matchesLabel != null) {
                matchesLabel.setText("Количество совпадений: 0");
            }
            if (successImage != null) {
                successImage.setVisible(true); // показать картинку
            }
            return;
        }

        // ✅ если совпадения найдены — скрываем картинку
        if (successImage!= null) {
            successImage.setVisible(false);
        }

        if (matchesLabel != null) {
            matchesLabel.setText("Количество совпадений: " + positions.size());
        }

        // начинаем с первого совпадения
        currentMatchIndex = 0;
        highlightCurrent(pattern);
    }


    private void highlightCurrent(String pattern) {
        textFlow.getChildren().clear();

        if (positions.isEmpty() || currentMatchIndex < 0 || currentMatchIndex >= positions.size()) {
            Text all = new Text(documentText);
            all.setFill(Color.WHITE);
            textFlow.getChildren().add(all);
            return;
        }

        int matchPos = positions.get(currentMatchIndex);
        int patternLength = pattern.length();

        // до совпадения
        Text before = new Text(documentText.substring(0, matchPos));
        before.setFill(Color.WHITE);
        textFlow.getChildren().add(before);

        // совпадение (Label с фоном)
        javafx.scene.control.Label highlighted =
                new javafx.scene.control.Label(documentText.substring(matchPos, matchPos + patternLength));
        highlighted.setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
        textFlow.getChildren().add(highlighted);

        // остаток
        Text after = new Text(documentText.substring(matchPos + patternLength));
        after.setFill(Color.WHITE);
        textFlow.getChildren().add(after);

        // прокрутка
        Platform.runLater(() -> scrollToNode(highlighted));
    }

    private void scrollToNode(Node node) {
        if (node == null || scrollPane == null) return;

        Bounds contentBounds = node.localToScene(node.getBoundsInLocal());
        Bounds viewportBounds = scrollPane.getViewportBounds();

        double contentHeight = textFlow.getBoundsInLocal().getHeight();
        double nodeY = contentBounds.getMinY() + scrollPane.getVvalue() * contentHeight;

        double vValue = (nodeY - viewportBounds.getHeight() / 2) / contentHeight;
        scrollPane.setVvalue(Math.max(0, Math.min(1, vValue)));
    }

    // кнопка "Вверх"
    @FXML
    private void onPrevClicked(MouseEvent event) {
        if (!positions.isEmpty() && currentMatchIndex > 0) {
            currentMatchIndex--;
            highlightCurrent(searchField.getText());
        }
    }

    // кнопка "Вниз"
    @FXML
    private void onNextClicked(MouseEvent event) {
        if (!positions.isEmpty() && currentMatchIndex < positions.size() - 1) {
            currentMatchIndex++;
            highlightCurrent(searchField.getText());
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
