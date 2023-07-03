package Client.View.Game;

import Client.Controller.Controller;
import Client.Controller.ManageData;
import Client.Controller.MapMenuController;
import Client.Model.EditedMap;
import Client.View.Commands.MapMenuCommands;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.regex.Matcher;

import static Client.View.InputOutput.output;

public class EditMapGUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ArrayList<String> commands = new ArrayList<>();
        VBox vBox = new VBox();
        vBox.setPrefSize(500, 200);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);
        Scene scene = new Scene(vBox);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);
        vBox.getChildren().add(hBox);

        Label label = new Label("Enter your command: ");
        label.setScaleX(1.2);
        label.setScaleY(1.2);
        label.setMinWidth(130);
        hBox.getChildren().add(label);
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
        hBox.getChildren().add(startGame);
        startGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MapGUI.setFirstTime(true);
                MapGUI mapGUI = new MapGUI();
                try {
                    mapGUI.start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setSpacing(5);
        vBox.getChildren().add(hBox1);

        Button shareMap = new Button();
        shareMap.setText("Share");
        hBox1.getChildren().add(shareMap);
        shareMap.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                EditedMap editedMap = new EditedMap(Controller.currentUser, commands);
                Controller.editedMaps.add(editedMap);
                ManageData.saveMap(Controller.editedMaps);
            }
        });

        Button selectMap = new Button();
        selectMap.setText("Select edited maps");
        hBox1.getChildren().add(selectMap);
        selectMap.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (commands.size() != 0) {
                    output("You have already made changes you can not choose any other map", 'o');
                    return;
                }
                SelectMapGUI selectMapGUI = new SelectMapGUI();
                try {
                    selectMapGUI.start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });





        stage.setScene(scene);
        stage.show();
    }

    public void runCommand(String text) {
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
