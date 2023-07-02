package Client.View.Game;

import Client.Controller.Controller;
import Client.Controller.GameMenuController;
import Client.Controller.MapMenuController;
import Client.Model.Building.Storage.Storage;
import Client.Model.Game;
import Client.Model.Government;
import Client.Model.Map;
import Client.Model.User;
import Client.View.InputOutput;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static Client.Controller.GameMenuController.mapSize;

public class StartGameGUI extends Application {
    private static int counter = 0;
    @Override
    public void start(Stage stage) throws Exception {
        GameMenuController.game = new Game();
        GameMenu.numberOfTurns = 0;
        mapSize = 200;

        //Select map size
        Stage miniStage = new Stage();
        VBox selectMapSize = new VBox();
        selectMapSize.setPrefSize(300, 100);
        selectMapSize.setAlignment(Pos.CENTER);
        selectMapSize.setSpacing(10);
        Scene miniScene = new Scene(selectMapSize);

        HBox selectMapSizeHBox = new HBox();
        selectMapSizeHBox.setAlignment(Pos.CENTER);
        selectMapSizeHBox.setSpacing(10);
        selectMapSize.getChildren().add(selectMapSizeHBox);
        Text text = new Text();
        text.setText("Map size:");
        text.setScaleX(1.2);
        text.setScaleY(1.2);
        selectMapSizeHBox.getChildren().add(text);

        ChoiceBox<Integer> mapSizes = new ChoiceBox<>();
        mapSizes.getItems().add(200);
        mapSizes.getItems().add(400);
        mapSizes.getSelectionModel().select(mapSize / 200 - 1);
        mapSizes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            mapSize = newValue;
        });
        selectMapSizeHBox.getChildren().add(mapSizes);

        Button continueButton = new Button();
        continueButton.setText("Continue");
        selectMapSizeHBox.getChildren().add(continueButton);
        continueButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                miniStage.close();
            }
        });


        miniStage.setScene(miniScene);
        miniStage.showAndWait();



        //Create default map
        GameMenu.valueDefaults();
        Map map = new Map();
        GameMenuController.game.setMap(map);
        MapMenuController.initializeMap(mapSize);
        Government government = new Government(Controller.currentUser);
        GameMenuController.game.getGovernments().add(government);
        Game.currentGovernment = government;
        GameMenu.giveDefaultBuildings(government, counter);
        GameMenu.giveDefaultResources(government, (Storage) government.getBuildings().get(0));
        counter++;

        //Select users
        Pane mainPane = new Pane();
        Scene scene = new Scene(mainPane);
        mainPane.setPrefSize(300, 100);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setLayoutX(10);
        vBox.setLayoutY(10);
        mainPane.getChildren().add(vBox);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        vBox.getChildren().add(hBox);

        Text searchUser = new Text();
        searchUser.setText("Username:");
        searchUser.setScaleX(1.2);
        searchUser.setScaleY(1.2);
        hBox.getChildren().add(searchUser);


        TextField usernamePrompt = new TextField();
        usernamePrompt.setMaxWidth(250);
        usernamePrompt.setPromptText("username");
        hBox.getChildren().add(usernamePrompt);

        Text searchError = new Text();
        searchError.setText("There is no user " + "!");
        searchError.setFill(Color.RED);
        vBox.getChildren().add(searchError);
        usernamePrompt.textProperty().addListener((observable, oldValue, newValue) -> {
            searchError.setText(findError(newValue));
        });

        Button bt = new Button();
        bt.setShape(new Rectangle(4, 3));
        bt.setText("✓");
        hBox.getChildren().add(bt);
        bt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (searchError.getText().equals("")) addGovernment(usernamePrompt.getText());
            }
        });

        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setSpacing(10);
        vBox.getChildren().add(hBox1);

        Button startGame = new Button();
        startGame.setText("Start game");
        hBox1.getChildren().add(startGame);
        startGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (GameMenuController.game.getGovernments().size() == 1) {
                    InputOutput.output("Add other users", 'b');
                    return;
                }
                MapGUI mapGUI = new MapGUI();
                try {
                    mapGUI.start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Button editMap = new Button();
        editMap.setText("Edit map");

        hBox1.getChildren().add(editMap);
        editMap.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });


        stage.requestFocus();
        stage.setScene(scene);
        stage.show();
    }

    private static void addGovernment(String username) {
        User user = Controller.findUserByUsername(username);
        if (user == null) {
            InputOutput.output("User " + username + " not found", 'b');
            return;
        }
        boolean checkFlag = false;
        for (Government addedGovernment : GameMenuController.game.getGovernments()) {
            if (addedGovernment.getUser().getUsername().equals(username)) {
                InputOutput.output("User " + username + " has already been added", 'b');
                checkFlag = true;
                break;
            }
        }
        if (checkFlag) return;
        Government government = new Government(user);
        GameMenuController.game.getGovernments().add(government);
        GameMenu.giveDefaultBuildings(government, counter);
        GameMenu.giveDefaultResources(government, (Storage) government.getBuildings().get(0));
        counter++;
        InputOutput.output("User " + username + " added", 'c');
    }

    private static String findError(String username) {
        User user = Controller.findUserByUsername(username);
        if (user == null) {
            return "There is no user " + username + "!";
        }
        if (user == Controller.currentUser) {
            return "This is your username!";
        }
        return "";
    }

}