module Project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;
    requires java.desktop;
    requires java.xml.bind;
    requires jakarta.xml.bind;
    requires org.glassfish.jaxb.runtime;

    opens Client to jakarta.xml.bind;
    opens Server to jakarta.xml.bind;

    opens View.LoginRegister to javafx.fxml;
    opens View.Chatroom to javafx.graphics;
    exports View.Chatroom;
    exports View.LoginRegister;
    exports View.Start;

    opens View.Game to javafx.fxml;
    exports View.Game;
    
    opens View to javafx.graphics;
    exports View;

    opens Model to com.google.gson;
    exports Model;

    opens Controller to com.google.gson;
    exports Controller;
    exports View.Examples;
    opens View.Examples to javafx.fxml;
    exports Model.Chat;
    opens Model.Chat to com.google.gson;

}