package Client.View.Start;

import Client.Controller.Controller;
import Client.View.Chatroom.CreateChat;
import Client.View.Game.StartGameGUI;
import Client.View.LoginRegister.MainMenuGUI;
import Client.View.Main;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class StartMenuGUI extends Application {

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1020, 700);
        stage.setScene(scene);
        stage.setTitle("Stronghold Crusader");
        stage.show();

        Image image = new Image(Main.class.getResource("/Images/backgrounds/03.jpg").toString());

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
        Button startGame = new Button("Start Game");
        startGame.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                try {
                    StartGameGUI startGameGUI = new StartGameGUI();
                    startGameGUI.start(Controller.getStage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Button profileMenu = new Button("Profile Menu");
        profileMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                ProfileMenuGUI profileMenuGUI = new ProfileMenuGUI();
                profileMenuGUI.start(Controller.getStage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Button chatMenu = new Button("Chat Menu");
        chatMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                CreateChat createChat = new CreateChat();
                createChat.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Button friends = new Button("Friends");
        friends.setOnAction(actionEvent -> {
            FriendsMenuGUI friendsMenuGUI = new FriendsMenuGUI();
            try {
                friendsMenuGUI.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Button back = new Button("Back");
        back.setOnAction(actionEvent -> {
            MainMenuGUI mainMenuGUI = new MainMenuGUI();
            mainMenuGUI.start(stage);
        });
        startGame.setMinWidth(100);
        profileMenu.setMinWidth(100);
        chatMenu.setMinWidth(100);
        friends.setMinWidth(100);
        back.setMinWidth(100);
        vBox.getChildren().addAll(startGame, profileMenu, chatMenu,friends,back);
        root.setCenter(vBox);
    }
}
