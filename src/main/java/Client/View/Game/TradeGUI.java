package Client.View.Game;

import Client.Model.User;
import Client.Controller.Controller;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicReference;

import Client.Controller.TradeMenuController;

public class TradeGUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        root.setSpacing(5);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Trade Menu");
        stage.show();

        AtomicReference<User> selectedUser = new AtomicReference<>();
        javafx.scene.control.Button startTrade = new Button("Trade with user: ");
        startTrade.setOnAction(actionEvent -> {
            if (selectedUser.get() != null) {
                CreateTradeGUI createTradeGUI = new CreateTradeGUI();
                createTradeGUI.setSelectedUser(selectedUser.get());
                try {
                    createTradeGUI.start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        startTrade.setMinWidth(280);

        VBox vBox = new VBox();
        for (User user: Controller.users){
            BorderPane borderPane = new BorderPane();
            Text text1 = new Text("\t" + user.getUsername());
            text1.setFont(Font.font(15));
            javafx.scene.control.Button button = new javafx.scene.control.Button("Select");
            button.setOnAction(actionEvent -> {
                startTrade.setText("Trade with user: "+user.getUsername());
                selectedUser.set(user);
            });
            borderPane.setLeft(text1);
            borderPane.setRight(button);
            borderPane.setMinWidth(280);
            borderPane.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");
            vBox.getChildren().add(borderPane);
        }

        vBox.setAlignment(Pos.CENTER);
        javafx.scene.control.ScrollPane scrollPane = new javafx.scene.control.ScrollPane();
        scrollPane.setContent(vBox);
        scrollPane.setMinWidth(300);
        scrollPane.setMinWidth(300);

        root.getChildren().add(scrollPane);
        root.getChildren().add(startTrade);

        Button showList = new Button("Show Trade List");
        showList.setMinWidth(280);
        root.getChildren().add(showList);
        showList.setOnAction(actionEvent -> {
            System.out.println(TradeMenuController.showList());
        });

    }
}
