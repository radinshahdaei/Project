package View.LoginRegister;

import Controller.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuGUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 200, 150);
        stage.setScene(scene);
        stage.setTitle("Crusader");
        stage.show();

        Controller.setStage(stage);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        Button register = new Button("REGISTER");
        register.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                RegisterMenuGUI registerMenuGUI = new RegisterMenuGUI();
                registerMenuGUI.start(Controller.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Button login = new Button("LOGIN");
        login.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
               LoginMenuGUI loginMenuGUI = new LoginMenuGUI();
               loginMenuGUI.start(Controller.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Button exit = new Button("EXIT");
        exit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                Platform.exit();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        register.setMinWidth(100);
        login.setMinWidth(100);
        exit.setMinWidth(100);
        vBox.getChildren().addAll(register, login, exit);
        root.setCenter(vBox);
    }
}
