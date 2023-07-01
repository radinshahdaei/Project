package Server.View.Examples;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ScrollPaneExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Button button = new Button("button");
        root.setCenter(button);
        button.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            DialogPane dialogPane = alert.getDialogPane();
            VBox vbox = new VBox();
            vbox.setSpacing(10);

            Text text = new Text("test");
            vbox.getChildren().add(text);

            // Add some labels to the VBox
            for (int i = 1; i <= 100; i++) {
                Label label = new Label("Label " + i);
                vbox.getChildren().add(label);
            }

            vbox.setAlignment(Pos.CENTER);

            // Create a ScrollPane and set its content
            ScrollPane scrollPane = new ScrollPane(vbox);
            scrollPane.setFitToWidth(true);

            dialogPane.setMaxHeight(400);
            dialogPane.setMinWidth(300);

            dialogPane.setContent(scrollPane);
            alert.showAndWait();
        });

        Scene scene = new Scene(root, 300, 200);

        // Set the Scene and show the Stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("ScrollPane Example");
        primaryStage.show();
    }

    private static class CustomDialog extends Dialog<Void> {
        public CustomDialog() {
            getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
