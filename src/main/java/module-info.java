module com.example.kmpjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.kmpjavafx to javafx.fxml;
    exports com.example.kmpjavafx;
}