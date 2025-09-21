package com.example.kmpjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;

import java.awt.event.MouseEvent;

public class PageNext2Controller {

    @FXML
    private TextField searchField;

    @FXML
    private TextFlow textFlow;

    // обработчик клика по иконке "Back.png"
    @FXML
    private void openHelloView(MouseEvent event) {
        System.out.println("openHelloView вызван!");
        // Здесь можно сделать переход на нужную сцену
    }

    @FXML
    private void initialize() {
        System.out.println("PageNext2Controller инициализирован");
    }
}
