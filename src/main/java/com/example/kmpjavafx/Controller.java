package com.example.kmpjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.List;

public class Controller {

    @FXML
    private TextField textField;

    @FXML
    private TextField patternField;

    @FXML
    private Button searchButton;

    @FXML
    private TextArea logArea;

    @FXML
    private void initialize() {
        searchButton.setOnAction(event -> {
            logArea.clear();
            String text = textField.getText();
            String pattern = patternField.getText();

            if (text.isEmpty() || pattern.isEmpty()) {
                logArea.appendText("Введите текст и шаблон.\n");
                return;
            }

            KMP kmp = new KMP(logArea);
            List<Integer> result = kmp.search(text, pattern);

            logArea.appendText("\nРезультат: " + result + "\n");
        });
    }
}
