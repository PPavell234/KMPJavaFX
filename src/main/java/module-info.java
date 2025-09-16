module com.example.kmpjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;


    opens com.example.kmpjavafx to javafx.fxml;
    exports com.example.kmpjavafx;
}