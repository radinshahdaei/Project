package Client.View.Chatroom;

import Client.Model.Chat.Chat;
import Client.Model.Chat.Message;
import Client.Controller.Controller;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.util.Iterator;

public class Chatroom extends Application {
    Chat chat = new Chat();

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        Scene scene = new Scene(root, 400, 600);
        stage.setScene(scene);
        stage.setTitle(chat.name);
        stage.show();

        TextField typeMessage = new TextField();
        typeMessage.setPromptText("type your message...");
        Button sendMessage = new Button("send");
        typeMessage.setMinWidth(330);
        sendMessage.setMinWidth(45);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);
        hBox.getChildren().addAll(typeMessage,sendMessage);
        root.getChildren().add(hBox);
        hBox.setLayoutX(10);
        hBox.setLayoutY(565);

        sendMessage.setOnAction(actionEvent -> {
            if (!typeMessage.getText().trim().equals("")) {
                createMessage(typeMessage.getText());
                clearMessages(root);
                showMessages(root);
                typeMessage.setText("");
            }
        });
        showMessages(root);


    }

    public void clearMessages(Pane root){
        Iterator<Node> iterator = root.getChildren().iterator();
        while (iterator.hasNext()){
            Node node = iterator.next();
            if (!(node instanceof HBox)) iterator.remove();
        }
    }

    public void showMessages(Pane root){
        int size = chat.getMessages().size();
        int counter = 0;
        for (Message message: chat.getMessages()){
            Pane messageBox = getMessage(message);
            root.getChildren().add(messageBox);
            messageBox.setLayoutX(10);
            messageBox.setLayoutY(565 - 90*(size - counter));
            counter++;

        }
    }

    public Pane getMessage(Message message){
        if (!message.getSender().equals(Controller.currentUser)) message.setSeen(true);
        Pane messageBox = new Pane();
        messageBox.setMaxWidth(200);
        messageBox.setMaxHeight(80);
        ImageView imageView = new ImageView(new Image(message.getSender().getImageUrl()));
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        Text username = new Text(message.getSender().getUsername());
        Text time = new Text(message.getTime());
        Text messageString = new Text(message.getMessage());
        Text seen = new Text();
        if (message.isSeen()) seen.setText("✓✓");
        else seen.setText("✓");

       messageBox.setStyle("-fx-background-color: #0088cc;");
       messageBox.getChildren().addAll(username,time,messageString,seen);
       username.setLayoutX(35);
       username.setLayoutY(10);

       imageView.setLayoutX(10);
       imageView.setLayoutY(10);

        messageString.setLayoutX(10);
       messageString.setLayoutY(50);

       time.setLayoutX(10);
       time.setLayoutY(70);

       seen.setLayoutX(180);
       seen.setLayoutY(70);

       messageBox.setOnMouseClicked(mouseEvent -> {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           DialogPane dialogPane = alert.getDialogPane();
           VBox vBox = new VBox();
           vBox.setAlignment(Pos.CENTER);
           vBox.setSpacing(5);
           TextField editText = new TextField();
           editText.setPromptText("edit");
           Button edit = new Button("Edit");
           Button delete = new Button("Delete");
           HBox hBox = new HBox(edit,delete);
           hBox.setAlignment(Pos.CENTER);
           hBox.setSpacing(5);
           vBox.getChildren().addAll(editText,hBox);
           edit.setOnAction(actionEvent -> {
               if (!editText.getText().trim().equals("")) messageString.setText(editText.getText());
           });
           delete.setOnAction(actionEvent -> {
               messageString.setText("This message has been deleted");
           });
           dialogPane.setContent(vBox);
           alert.showAndWait();

       });
       return messageBox;
    }

    public void createMessage(String message){
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        chat.getMessages().add(new Message(message,formattedTime,false, Controller.currentUser));
    }

}
