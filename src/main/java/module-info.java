module com.example.nyabankomaten {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.nyabankomaten to javafx.fxml;
    exports com.example.nyabankomaten;
}