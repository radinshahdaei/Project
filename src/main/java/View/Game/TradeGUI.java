package View.Game;

import Controller.Controller;
import Model.User;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicReference;

import Controller.TradeMenuController;

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
            javafx.scene.control.Button button = new javafx.scene.control.Button("Select");
            button.setOnAction(actionEvent -> {
                startTrade.setText("Trade with user: "+user.getUsername());
                selectedUser.set(user);
            });
            borderPane.setLeft(text1);
            borderPane.setRight(button);
            borderPane.setMinWidth(280);
            borderPane.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
            vBox.getChildren().add(borderPane);
        }

        vBox.setAlignment(Pos.CENTER);
        javafx.scene.control.ScrollPane scrollPane = new javafx.scene.control.ScrollPane();
        scrollPane.setContent(vBox);
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
