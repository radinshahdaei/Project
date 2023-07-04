package Client.View.Game;

import Client.Controller.Controller;
import Client.Model.EditedMap;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SelectMapGUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        root.setSpacing(15);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Map Selection Menu");

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        for (EditedMap editedMap: Controller.editedMaps) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            hBox.setSpacing(55);
            vBox.getChildren().add(hBox);
            Text text = new Text();
            text.setText(editedMap.getUser().getUsername());
            hBox.getChildren().add(text);

            Button select = new Button();
            select.setText("Select");
            select.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (String command: editedMap.getCommands()) {
                        EditMapGUI editMapGUI = new EditMapGUI();
                        editMapGUI.runCommand(command);
                    }
                    MapGUI.setFirstTime(true);
                    MapGUI mapGUI = new MapGUI();
                    try {
                        mapGUI.start(stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            hBox.getChildren().add(select);

            Button view = new Button();
            view.setText("View");
            view.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                }
            });
            hBox.getChildren().add(view);
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vBox);
        scrollPane.setMinWidth(300);
        root.getChildren().add(scrollPane);
        stage.show();
    }
}
