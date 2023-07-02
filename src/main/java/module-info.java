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
    opens Client.Model.Chat to jakarta.xml.bind;

    opens Client.View.LoginRegister to javafx.fxml;
    opens Client.View.Chatroom to javafx.graphics;
    exports Client.View.Chatroom;
    exports Client.View.LoginRegister;
    exports Client.View.Start;

    exports Client.View.Game;
    
    opens Client.View to javafx.graphics;
    exports Client.View;

    opens Client.Model to com.google.gson;
    exports Client.Model;

    opens Client.Controller to com.google.gson;
    exports Client.Controller;
    exports Client.View.Examples;
    opens Client.View.Examples to javafx.fxml;
    exports Client.Model.Chat;
    opens Client.View.Game to javafx.fxml, javafx.graphics;


}