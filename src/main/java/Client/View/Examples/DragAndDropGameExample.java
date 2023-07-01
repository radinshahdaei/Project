package Client.View.Examples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DragAndDropGameExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a root pane to hold the game elements
        Pane root = new Pane();

        // Load building images
        Image buildingImage;// =  new Image(Game.class.getResource(buffer).toString());
        buildingImage = new Image("building.png");

        // Create an ImageView for the building
        ImageView buildingImageView = new ImageView(buildingImage);
        buildingImageView.setFitWidth(100);
        buildingImageView.setFitHeight(100);

        // Add drag and drop functionality to the building
        buildingImageView.setOnDragDetected(event -> {
            Dragboard dragboard = buildingImageView.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString("building");
            dragboard.setContent(content);
            event.consume();
        });

        buildingImageView.setOnDragOver(event -> {
            if (event.getGestureSource() != buildingImageView && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        buildingImageView.setOnDragEntered(event -> {
            if (event.getGestureSource() != buildingImageView && event.getDragboard().hasString()) {
                // Apply visual effect when the building is being dragged over
                buildingImageView.setStyle("-fx-effect: dropshadow(three-pass-box, red, 10, 0, 0, 0);");
            }
            event.consume();
        });

        buildingImageView.setOnDragExited(event -> {
            // Remove the visual effect when the building is dragged away
            buildingImageView.setStyle("");
            event.consume();
        });

        buildingImageView.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;
            if (dragboard.hasString()) {
                // Place the building at the drop location
                buildingImageView.relocate(event.getX() - buildingImageView.getFitWidth() / 2,
                        event.getY() - buildingImageView.getFitHeight() / 2);
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });

        // Add the building ImageView to the root pane
        root.getChildren().add(buildingImageView);

        // Create the scene
        Scene scene = new Scene(root, 800, 600);

        // Set up the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Drag and Drop Game");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
