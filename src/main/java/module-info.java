module com.vanier.easygrapher {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.vanier.easygrapher to javafx.fxml;
    exports com.vanier.easygrapher;
}
