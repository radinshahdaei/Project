package Client.View.Game;

import Client.Controller.Controller;
import Client.Controller.GameMenuController;
import Client.Controller.ManageData;
import Client.Controller.MapMenuController;
import Client.Model.EditedMap;
import Client.View.Commands.MapMenuCommands;
import Client.View.Game.MapGUI;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.regex.Matcher;

import static Client.View.InputOutput.input;
import static Client.View.InputOutput.output;

public class EditMapGUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ArrayList<String> commands = new ArrayList<>();
        VBox vBox = new VBox();
        vBox.setPrefSize(750, 750);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        Scene scene = new Scene(vBox);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(25);
        vBox.getChildren().add(hBox);

        Text text = new Text();
        text.setText("Enter your command here: ");
        text.setScaleX(1.2);
        text.setScaleY(1.2);
        hBox.getChildren().add(text);
        TextField textField = new TextField();
        textField.setMaxWidth(1250);
        textField.setPromptText("command");
        hBox.getChildren().add(textField);

        Button submit = new Button();
        submit.setText("Submit");
        submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                commands.add(textField.getText());
                runCommand(textField.getText());
                textField.setText("");
            }
        });
        hBox.getChildren().add(submit);

        Button startGame = new Button();
        startGame.setText("Start game");
        vBox.getChildren().add(startGame);
        startGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                EditedMap editedMap = new EditedMap(Controller.currentUser, commands);
                ManageData.saveMap(editedMap);
                MapGUI.setFirstTime(true);
                MapGUI mapGUI = new MapGUI();
                try {
                    mapGUI.start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });





        stage.setScene(scene);
        stage.show();
    }

    private void runCommand(String text) {
        Matcher matcher;
        if (text.matches("\\s*show\\s+related\\s+commands\\s*")) {
            output("end map\nsettexture -x <X> -y <Y> -t <type>\nsettexture -x1 <X1> -x2 <X2> -y1 <Y1> -y2 <Y2> -t <type>\nclear -x <X> -y <Y>\ndraw map\nend draw map\nmap move up <amountUp> down <amountDown> left <amountLeft> right <amountRight>\nshow details -x <X> -y <Y>\ndropbuilding -x <X> -y <Y> -t <type>", 'k');
        } else if ((matcher = MapMenuCommands.getMatcher(text, MapMenuCommands.SET_TEXTURE)) != null) {
            String type = matcher.group("type");
            int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
            MapMenuController.setTexture(type, x, y);
        } else if ((matcher = MapMenuCommands.getMatcher(text, MapMenuCommands.SET_TEXTURE_REC)) != null) {
            String type = matcher.group("type");
            int x1 = Integer.parseInt(matcher.group("X1")), x2 = Integer.parseInt(matcher.group("X2")),
                    y1 = Integer.parseInt(matcher.group("Y1")), y2 = Integer.parseInt(matcher.group("Y2"));
            MapMenuController.setTextureRectangle(type, x1, x2, y1, y2);
        } else if ((matcher = MapMenuCommands.getMatcher(text, MapMenuCommands.CLEAR)) != null) {
            int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
            MapMenuController.clear(x, y);
        } else if ((matcher = MapMenuCommands.getMatcher(text, MapMenuCommands.SHOW_DETAILS)) != null) {
            int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
            MapMenuController.showDetails(x, y);
        } else if ((matcher = MapMenuCommands.getMatcher(text, MapMenuCommands.DROP_ROCK)) != null) {
            int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
            char direction = matcher.group("direction").charAt(0);
            MapMenuController.dropRock(x, y, direction);
        } else if ((matcher = MapMenuCommands.getMatcher(text, MapMenuCommands.DROP_TREE)) != null) {
            int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
            String type = matcher.group("type");
            MapMenuController.dropTree(x, y, type);
        } else {
            output("Invalid command!", 'j');
        }
    }
}
