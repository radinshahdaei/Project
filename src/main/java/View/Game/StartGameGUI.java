package View.Game;

import Controller.Controller;
import Model.Building.Storage.Storage;
import Model.Game;
import Model.Government;
import Model.Map;
import Model.User;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import Controller.GameMenuController;
import Controller.MapMenuController;

import static View.Game.GameMenu.giveDefaultBuildings;
import static View.Game.GameMenu.giveDefaultResources;


public class StartGameGUI extends Application {
    ArrayList<AtomicReference<User>> selectedUsers = new ArrayList<>();
    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        root.setSpacing(5);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 400, 200);
        stage.setScene(scene);
        stage.setTitle("Start Game");
        stage.show();


        javafx.scene.control.Button startGame = new Button("Start game with users: ");


        VBox vBox = new VBox();
        for (User user: Controller.users){
            BorderPane borderPane = new BorderPane();
            Text text1 = new Text("\t" + user.getUsername());
            text1.setFont(Font.font(15));
            javafx.scene.control.Button button = new javafx.scene.control.Button("Select");
            button.setOnAction(actionEvent -> {
                if (!hasShit(user)) {
                    selectedUsers.add(new AtomicReference<>(user));
                    startGame.setText(startGame.getText()+user.getUsername()+" + ");
                }
            });
            borderPane.setLeft(text1);
            borderPane.setRight(button);
            borderPane.setMinWidth(380);
            borderPane.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");
            vBox.getChildren().add(borderPane);
        }

        vBox.setAlignment(Pos.CENTER);
        javafx.scene.control.ScrollPane scrollPane = new javafx.scene.control.ScrollPane();
        scrollPane.setContent(vBox);
        scrollPane.setMinWidth(400);
        scrollPane.setMinWidth(400);

        root.getChildren().add(scrollPane);
        root.getChildren().add(startGame);

        ComboBox<String> selectMap = new ComboBox<>();
        selectMap.getItems().addAll("200*200 map","400*400 map");
        root.getChildren().add(selectMap);
        selectMap.setValue("200*200 map");

        startGame.setOnAction(actionEvent -> {
            if (selectedUsers.size() != 0){
                GameMenuController.game = new Game();
                Map map = new Map();
                GameMenuController.game.setMap(map);
                int mapSize = 400;
                if (selectMap.getValue().equals("200*200 map")) mapSize = 200;
                MapMenuController.initializeMap(mapSize);

                int counter = 0;

                Government government = new Government(Controller.currentUser);
                GameMenuController.game.getGovernments().add(government);
                Game.currentGovernment = government;
                giveDefaultBuildings(government, counter);
                giveDefaultResources(government, (Storage) government.getBuildings().get(0));
                counter++;

                for (AtomicReference<User> atomicReference:selectedUsers){
                    User user = atomicReference.get();
                    government = new Government(user);
                    GameMenuController.game.getGovernments().add(government);
                    giveDefaultBuildings(government, counter);
                    giveDefaultResources(government, (Storage) government.getBuildings().get(0));
                    counter++;
                }

                MapGUI mapGUI = new MapGUI();
                try {
                    mapGUI.start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }

        });
    }

    public boolean hasShit(User user){
        for (AtomicReference<User> atomicReference:selectedUsers){
            if (atomicReference.get().equals(user)) return true;
        }
        return false;
    }
}
