module Project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;

    opens View.LoginRegister to javafx.fxml;
    exports View.LoginRegister;

    opens Model to com.google.gson;
    exports Model;

    opens Controller to com.google.gson;
    exports Controller;

}