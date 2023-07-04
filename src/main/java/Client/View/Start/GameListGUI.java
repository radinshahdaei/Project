package Client.View.Start;

import Client.Controller.Controller;
import Client.Model.GameInvite.GameInvite;
import Client.View.Game.StartGameGUI;
import Client.View.InputOutput;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GameListGUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 500, 200);
        stage.setScene(scene);
        stage.setTitle("Game List");
        stage.show();

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        vBox.getChildren().add(hBox);

        javafx.scene.control.Button createGame = new Button("Create Invite");
        createGame.setOnAction(actionEvent -> {
            new GameInvite(Controller.currentUser.getId());
            InputOutput.output("game invite created, waiting for users.",'p');
            try {
                Controller.client.sendGameInvites();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        hBox.getChildren().add(createGame);

        Button refresh = new Button("refresh");
        refresh.setOnAction(actionEvent -> refresh(vBox));
        hBox.getChildren().add(refresh);

        createGame.setMinWidth(340);
        refresh.setMinWidth(140);

        for (GameInvite gameInvite:GameInvite.allGameInvites){
            BorderPane borderPane = new BorderPane();
            borderPane.setLeft(gameInviteInformation(gameInvite));
            Button join = new Button("join");
            join.setOnAction(actionEvent -> {
                if (!gameInvite.getAdminId().equals(Controller.currentUser.getId())){
                    gameInvite.joinGame(Controller.currentUser.getId());
                    InputOutput.output("You have joined this game!",'p');
                    try {
                        Controller.client.sendGameInvites();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    InputOutput.output("You have already joined this game!",'p');
                    try {
                        Controller.client.sendGameInvites();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            });
            hBox.getChildren().add(join);
            if (gameInvite.getAdminId().equals(Controller.currentUser.getId())) {
                Button start = new Button("start");
                start.setOnAction(actionEvent -> {
                    InputOutput.output("Game started!",'p');
                    StartGameGUI startGameGUI = new StartGameGUI();
                    try {
                        startGameGUI.start(new Stage());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    hBox.getChildren().add(start);
                });
            }
            borderPane.setRight(hBox);
            vBox.getChildren().add(borderPane);
        }

        root.setCenter(vBox);

    }

    public void refresh(VBox vBox){
        int size = vBox.getChildren().size();
        vBox.getChildren().remove(1,size);
        for (GameInvite gameInvite:GameInvite.allGameInvites){
            BorderPane borderPane = new BorderPane();
            borderPane.setLeft(gameInviteInformation(gameInvite));
            HBox hBox = new HBox();
            Button join = new Button("join");
            join.setOnAction(actionEvent -> {
                if (!gameInvite.getAdminId().equals(Controller.currentUser.getId())){
                    gameInvite.joinGame(Controller.currentUser.getId());
                    InputOutput.output("You have joined this game!",'p');
                    try {
                        Controller.client.sendGameInvites();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    InputOutput.output("You have already joined this game!",'p');
                    try {
                        Controller.client.sendGameInvites();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            hBox.getChildren().add(join);
            if (gameInvite.getAdminId().equals(Controller.currentUser.getId())) {
                Button start = new Button("start");
                hBox.getChildren().add(start);
                start.setOnAction(actionEvent -> {
                    InputOutput.output("Game started!",'p');
                    StartGameGUI startGameGUI = new StartGameGUI();
                    try {
                        startGameGUI.start(new Stage());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                });
            }
            borderPane.setRight(hBox);
            vBox.getChildren().add(borderPane);
        }
    }

    public Label gameInviteInformation(GameInvite gameInvite){
        Label label = new Label();
        StringBuilder text = new StringBuilder("Admin: ");
        text.append(Objects.requireNonNull(Controller.findUserById(gameInvite.getAdminId())).getUsername());
        text.append("| Joined users: ");
        int counter = 0;
        for (String id:gameInvite.getAllUsersId()){
            counter++;
            if (!id.equals(gameInvite.getAdminId())) {
                text.append(Objects.requireNonNull(Controller.findUserById(id)).getNickname());
                if (counter != gameInvite.getAllUsersId().size()) text.append(", ");
            }
        }
        int number = 5 - gameInvite.getAllUsersId().size();
        text.append("| capacity: ").append(number).append("/5");
        label.setText(text.toString());
        return label;
    }
}
