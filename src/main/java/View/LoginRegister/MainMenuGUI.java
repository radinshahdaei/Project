package View.LoginRegister;

import View.Main;
import Controller.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainMenuGUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1020, 700);
        stage.setScene(scene);
        stage.setTitle("Stronghold Crusader");
        stage.show();

        Image image = new Image(Main.class.getResource("/Images/backgrounds/01.jpg").toString());

        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );

        // Create a background
        Background background = new Background(backgroundImage);

        // Set the background on the root pane
        root.setBackground(background);

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
