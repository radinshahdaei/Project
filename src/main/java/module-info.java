module Project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;
    requires java.desktop;

    opens View.LoginRegister to javafx.fxml;
    exports View.LoginRegister;
    exports View.Start;

    opens View.Game to javafx.fxml;
    exports View.Game;
    
    opens View to javafx.graphics;
    exports  View;

    opens Model to com.google.gson;
    exports Model;

    opens Controller to com.google.gson;
    exports Controller;
    exports View.Examples;
    opens View.Examples to javafx.fxml;

}