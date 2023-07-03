package Client.View.Start;

import Client.Controller.Controller;
import Client.Controller.ManageData;
import Client.Model.User;
import Client.View.InputOutput;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class FriendsMenuGUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        Scene scene = new Scene(root, 385, 150);
        stage.setScene(scene);
        stage.setTitle("Friends");
        stage.show();

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setLayoutX(10);
        vBox.setLayoutY(10);
        root.getChildren().add(vBox);

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
        usernamePrompt.textProperty().addListener((observable, oldValue, newValue) -> {
            searchError.setText(findError(newValue,vBox));
        });

        Button bt = new Button();
        bt.setMinWidth(75);
        bt.setShape(new Rectangle(4, 3));
        bt.setText("Send Friend Request");
        hBox.getChildren().add(bt);
        bt.setOnAction(actionEvent -> {
            if (searchError.getText().equals("")) {
                User user = Controller.findUserByUsername(usernamePrompt.getText());
                assert user != null;
                boolean result = user.addToFriendRequests(Controller.currentUser.getId());
                if (!result) InputOutput.output("You have already sent a friend request!\nor This user is already your friend!", 'b');
                else {
                    InputOutput.output("Friend request sent to "+user.getUsername(), 'c');
                    ManageData.saveUsers();
                    try {
                        Controller.client.sendDatabase();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        Button btt = new Button("Show friend requests");
        btt.setMinWidth(75);
        btt.setShape(new Rectangle(4, 3));
        btt.setOnAction(actionEvent -> {
            showFriendRequests(Controller.currentUser);
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(searchError);
        borderPane.setRight(btt);

        vBox.getChildren().add(borderPane);
    }

    public static void showFriendRequests(User user){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        DialogPane dialogPane = alert.getDialogPane();
        VBox vbox = new VBox();
        vbox.setSpacing(10);

        alert.setHeaderText("Friend Requests");

        int index = 1;
        for (String id:user.getPendingFriendRequestsId()){
            BorderPane borderPane = new BorderPane();
            Label label = new Label();
            label.setText(index+") Username: "+user.getUsername());
            HBox buttons = new HBox();
            Button accept = new Button("accept");
            accept.setOnAction(actionEvent -> {
                InputOutput.output(Controller.findUserById(id).getUsername()+" added as friend!", 'c');
                ManageData.saveUsers();
                try {
                    Controller.client.sendDatabase();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
            Button reject = new Button("reject");
            reject.setOnAction(actionEvent -> {
                user.removeFromFriendRequests(id);
                InputOutput.output("Friend request from "+Controller.findUserById(id).getUsername()+" rejected!", 'b');
                ManageData.saveUsers();
                try {
                    Controller.client.sendDatabase();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            buttons.getChildren().addAll(accept,reject);
            borderPane.setLeft(label);
            borderPane.setRight(buttons);
            borderPane.setMinWidth(420);
            vbox.getChildren().add(borderPane);
            index++;
        }

        vbox.setAlignment(Pos.CENTER);
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);

        dialogPane.setMaxHeight(420);
        dialogPane.setMinWidth(300);

        dialogPane.setContent(scrollPane);
        alert.showAndWait();
    }

    private static String findError(String username,VBox vBox) {
        if (vBox.getChildren().size() == 3) vBox.getChildren().remove(2);
        User user = Controller.findUserByUsername(username);
        if (user == null) {
            return "There is no user " + username + "!";
        }
        if (user == Controller.currentUser) {
            return "This is your username!";
        }
        vBox.getChildren().add(getProfile(user));
        return "";
    }

    public static BorderPane getProfile(User user){

        BorderPane borderPane = new BorderPane();
        ImageView imageView = new ImageView(new Image(user.getImageUrl()));
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        Label label = new Label();
        label.setText("Username: "+user.getUsername()+", Nickname: "+user.getNickname()+", HighScore: "+user.getHighScore());
        borderPane.setLeft(label);
        borderPane.setRight(imageView);
        borderPane.setMinWidth(350);
        label.setStyle("-fx-font-size: 13px;");
        borderPane.setStyle("-fx-padding: 20px; -fx-background-color: #f0f0f0;");
        return borderPane;
    }
}
