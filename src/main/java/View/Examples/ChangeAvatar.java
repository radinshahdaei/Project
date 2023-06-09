package View.Examples;

import Controller.Controller;
import Model.Game;
import View.Start.ProfileMenuGUI;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

import Controller.ManageData;

public class ChangeAvatar extends Application {

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 450, 250);
        stage.setScene(scene);
        stage.setTitle("Change Avatar");
        stage.show();

        HBox hBox = new HBox();


        AtomicReference<Image> image = new AtomicReference<>();
        AtomicReference<String> url = new AtomicReference<>();




        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);

        Label filePathLabel = new Label("Drag and drop a file here");

        Rectangle rectangle = new Rectangle(200,100);
        rectangle.setFill(Color.BLUE);

        rectangle.setOnDragOver(event -> {
            if (event.getGestureSource() != vbox && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });
        rectangle.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;
            if (dragboard.hasFiles()) {
                File file = dragboard.getFiles().get(0);
                image.set(new Image(file.toURI().toString()));
                url.set(file.toURI().toString());
                setAvatar(image,hBox);
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });

        vbox.getChildren().addAll(filePathLabel,rectangle);

        Button optionalAvatar = new Button("Choose from file");
        optionalAvatar.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
            );

            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                image.set(new Image(selectedFile.toURI().toString()));
                url.set(selectedFile.toURI().toString());
                setAvatar(image,hBox);

            }
        });
        optionalAvatar.setMinWidth(200);

        vbox.getChildren().add(optionalAvatar);

        ComboBox<String> choices = new ComboBox<>();
        choices.setItems(FXCollections.observableArrayList("1","2","3","4","5","5","6","7","8","9","10"));
        choices.setMaxWidth(200);
        choices.setPromptText("Choose from options");

        choices.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String buffer = "/Images/Avatars/"+newValue+".png";
            image.set(new Image(Game.class.getResource(buffer).toString()));
            url.set(Game.class.getResource(buffer).toString());
            setAvatar(image,hBox);

        });

        vbox.getChildren().add(choices);



        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.getChildren().add(vbox);

        image.set(new Image(Controller.currentUser.getImageUrl()));
        setAvatar(image,hBox);

        root.setCenter(hBox);

        Button back = new Button("Back");
        back.setOnAction(actionEvent -> {
            ProfileMenuGUI profileMenuGUI = new ProfileMenuGUI();
            try {
                profileMenuGUI.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Button confirm = new Button("Confirm");
        confirm.setOnAction(actionEvent -> {
            Controller.currentUser.setImageUrl(url.toString());
            ManageData.saveCurrentUser();
            ManageData.saveUsers();
            ProfileMenuGUI profileMenuGUI = new ProfileMenuGUI();
            try {
                profileMenuGUI.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        back.setMinWidth(95);
        confirm.setMinWidth(95);
        HBox buttons = new HBox();
        buttons.getChildren().addAll(back,confirm);
        buttons.setSpacing(10);
        buttons.setMinWidth(200);

        vbox.getChildren().add(buttons);
    }



    public void setAvatar(AtomicReference<Image> image,HBox hBox){
        ImageView imageView = new ImageView(image.get());
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        if (hBox.getChildren().size() == 2) hBox.getChildren().remove(1);
        hBox.getChildren().add(imageView);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
