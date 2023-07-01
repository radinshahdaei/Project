package View.Chatroom;

import Controller.Controller;
import Model.Chat.Chat;
import Model.User;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class CreateChat extends Application {
    ArrayList<AtomicReference<User>> selectedUsers = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        root.setSpacing(5);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 400, 200);
        stage.setScene(scene);
        stage.setTitle("Create Chat");
        stage.show();


        javafx.scene.control.Button startChat = new Button("Create chat with users: ");

        VBox vBox = new VBox();
        for (User user: Controller.users){
            BorderPane borderPane = new BorderPane();
            Text text1 = new Text("\t" + user.getUsername());
            text1.setFont(Font.font(15));
            javafx.scene.control.Button button = new javafx.scene.control.Button("Select");
            button.setOnAction(actionEvent -> {
                if (!hasShit(user)) {
                    selectedUsers.add(new AtomicReference<>(user));
                    startChat.setText(startChat.getText()+user.getUsername()+" + ");
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
        root.getChildren().add(startChat);

        startChat.setOnAction(actionEvent -> {
            Chat chat = new Chat();
            for (AtomicReference<User> atomicReference:selectedUsers){
                chat.getUsers().add(atomicReference.get());
            }
            Chat.allChats.add(chat);
            Chatroom chatroom = new Chatroom();
            chatroom.setChat(chat);
            try {
                chatroom.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
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
