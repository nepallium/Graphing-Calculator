module com.vanier.easygrapher {
    requires javafx.controls;
    requires javafx.fxml;

    exports Model;
    opens Model to javafx.fxml;
    exports Main;
    opens Main to javafx.fxml;
    exports Controller;
    opens Controller to javafx.fxml;
}
