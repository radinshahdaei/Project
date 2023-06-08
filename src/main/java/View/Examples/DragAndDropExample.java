package View.Examples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class DragAndDropExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a VBox to hold the dropped file information
        VBox vbox = new VBox();
        vbox.setSpacing(10);

        // Create a label to display the dropped file path
        Label filePathLabel = new Label("Drag and drop a file here");

        // Handle the drag over event
        vbox.setOnDragOver(event -> {
            if (event.getGestureSource() != vbox && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        // Handle the file drop event
        vbox.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;
            if (dragboard.hasFiles()) {
                File file = dragboard.getFiles().get(0);
                filePathLabel.setText(file.getAbsolutePath());
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });

        // Add the label to the VBox
        vbox.getChildren().add(filePathLabel);

        // Create the scene
        Scene scene = new Scene(vbox, 400, 200);

        // Set up the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Drag and Drop Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
