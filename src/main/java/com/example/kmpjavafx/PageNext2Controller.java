package com.example.kmpjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.awt.event.MouseEvent;

public class PageNext2Controller {

    @FXML
    private TextFlow textFlow;

    private String documentText = "";

    // Вызывается из PageNext1Controller
    public void setDocumentText(String text) {
        this.documentText = text;

        // Отобразим в TextFlow
        textFlow.getChildren().clear();
        textFlow.getChildren().add(new Text(text));
    }

    @FXML
    private void initialize() {
        System.out.println("PageNext2Controller инициализирован");
    }
}
