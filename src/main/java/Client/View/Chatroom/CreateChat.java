package Client.View.Chatroom;

import Client.Controller.Controller;
import Client.Model.Chat.Chat;
import Client.Model.User;
import Client.View.Start.StartMenuGUI;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
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

        selectedUsers.add(new AtomicReference<>(Controller.currentUser));

        javafx.scene.control.Button startChat = new Button("Create chat with users: "+Controller.currentUser.getUsername());

        VBox vBox = new VBox();
        for (User user: Controller.users){
            if (user.equals(Controller.currentUser)) continue;
            BorderPane borderPane = new BorderPane();
            Text text1 = new Text("\t" + user.getUsername());
            text1.setFont(Font.font(15));
            javafx.scene.control.Button button = new javafx.scene.control.Button("Select");
            button.setOnAction(actionEvent -> {
                if (!hasShit(user)) {
                    selectedUsers.add(new AtomicReference<>(user));
                    startChat.setText(startChat.getText()+" + "+user.getUsername());
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
            chat.setId(LocalTime.now().getNano());
            for (AtomicReference<User> atomicReference:selectedUsers){
                chat.getUsers().add(atomicReference.get());
            }
            if (chat.getUsers().size() == 2){
                chat.name = "private chat with "+chat.getUsers().get(1).getUsername();
            } else  if (chat.getUsers().size() > 2){
                String name = "room with ";
                for (User user : chat.getUsers()) {
                    if (user.equals(Controller.currentUser)) continue;
                    else name+=user.getUsername()+"/ ";
                }
                chat.name = name;
            } else {
                chat.name = "saved messages for "+Controller.currentUser.getUsername();
            }
            Chat.allChats.add(chat);
            Chatroom chatroom = new Chatroom();
            chatroom.setChat(chat);
            try {
                chatroom.start(new Stage());
                // Chat.debugKonesh();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Button showAllChats = new Button("Show your chats");
        showAllChats.setOnAction(actionEvent -> {
            for (Chat chat : Chat.allChats){
                for (User user : chat.getUsers()){
                    if (Controller.currentUser.getUsername().equals(user.getUsername())){
                        System.out.println(chat);
                        Chatroom chatroom = new Chatroom();
                        chatroom.setChat(chat);
                        try {
                            chatroom.start(new Stage());
                        try {
                            Controller.client.sendChats();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });

        showAllChats.setMinWidth(170);
        startChat.setMinWidth(350);
        Button back = new Button("back");
        back.setMinWidth(170);
        back.setOnAction(actionEvent -> {
            StartMenuGUI startMenuGUI = new StartMenuGUI();
            startMenuGUI.start(stage);
        });

        HBox hBox = new HBox();
        hBox.getChildren().addAll(showAllChats,back);
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.CENTER);
        root.getChildren().add(hBox);

    }


    public boolean hasShit(User user){
        for (AtomicReference<User> atomicReference:selectedUsers){
            if (atomicReference.get().equals(user)) return true;
        }
        return false;
    }
}
