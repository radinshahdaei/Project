package Client.View.Game;


import Client.Model.*;
import Client.Model.Resources.Resource;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

public class CreateTradeGUI extends Application {
    User selectedUser = null;

    public void setSelectedUser(User user){
        selectedUser = user;
    }
    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        root.setSpacing(5);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 300, 400);
        stage.setScene(scene);
        stage.setTitle("Create trade with "+selectedUser.getUsername());
        stage.show();

        Label label = new Label();

        VBox vbox = new VBox();
        Store.initializeCommodities();

        ArrayList<Pair<Commodity,Integer>> commodities = new ArrayList<>();

        for (Commodity commodity:Store.commodities){
            BorderPane borderPane = new BorderPane();
            Text text1 = new Text("\t" + commodity.resourceType.name);
            Text text2 = new Text("Stock: "+commodity.stock);
            text1.setFont(Font.font(15));
            text2.setFont(Font.font(15));
            javafx.scene.control.Button buttonPlus = new javafx.scene.control.Button("+");
            Button buttonMinus = new Button("-");

            buttonPlus.setOnAction(actionEvent -> {
                int amount = 0;
                Commodity commodity1 = null;
                Iterator<Pair<Commodity,Integer>> iterator = commodities.iterator();
                while (iterator.hasNext()){
                    Pair<Commodity,Integer> pair = null;
                    if (commodity.resourceType.name.equals((pair = iterator.next()).getKey().resourceType.name)) {
                        amount = pair.getValue();
                        commodity1 = pair.getKey();
                        iterator.remove();
                    }
                }
                ;
                commodities.add(new Pair<Commodity,Integer>(commodity,++amount));
                updateCommodityList(commodities,label);

            });

            buttonMinus.setOnAction(actionEvent -> {
                int amount = 0;
                Commodity commodity1 = null;
                Iterator<Pair<Commodity,Integer>> iterator = commodities.iterator();
                while (iterator.hasNext()){
                    Pair<Commodity,Integer> pair = null;
                    if (commodity.resourceType.name.equals((pair = iterator.next()).getKey().resourceType.name)) {
                        amount = pair.getValue();
                        commodity1 = pair.getKey();
                        iterator.remove();
                    }
                }
                if (amount > 1) commodities.add(new Pair<Commodity,Integer>(commodity,--amount));
                updateCommodityList(commodities,label);

            });

            HBox hBox = new HBox(buttonPlus,buttonMinus);
            borderPane.setLeft(text1);
            borderPane.setCenter(text2);
            borderPane.setRight(hBox);
            borderPane.setMinWidth(280);
            borderPane.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");
            vbox.getChildren().add(borderPane);
        }
        vbox.setAlignment(Pos.CENTER);
        javafx.scene.control.ScrollPane scrollPane = new javafx.scene.control.ScrollPane();
        scrollPane.setContent(vbox);
        scrollPane.setMaxHeight(300);

        scrollPane.setMinWidth(250);

        root.getChildren().add(scrollPane);
        root.getChildren().add(label);

        HBox hBox = new HBox();

        Button donate = new Button("Donate");
        donate.setOnAction(actionEvent -> {
            ArrayList<Resource> resources = new ArrayList<>();
            for (Pair<Commodity,Integer> commodityIntegerPair:commodities){
                resources.add(new Resource(commodityIntegerPair.getKey().resourceType,commodityIntegerPair.getValue()));
            }
            Trade trade = new Trade(resources, Game.currentGovernment,Game.getGovernmentByUser(selectedUser));

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("User Input");
            dialog.setHeaderText("Enter your message:");

            AtomicReference<String> message = new AtomicReference<>("");
            dialog.showAndWait().ifPresent(result -> {
                message.set(result.trim());
            });
            trade.setMessage(message.get());
            trade.setDonation(true);

            if (trade.toGovernment != null && trade.fromGovernment != null){
                trade.toGovernment.addTrade(trade);
                trade.fromGovernment.addTrade(trade);
            }
        });

        Button request = new Button("Request");
        request.setOnAction(actionEvent -> {
            ArrayList<Resource> resources = new ArrayList<>();
            for (Pair<Commodity,Integer> commodityIntegerPair:commodities){
                resources.add(new Resource(commodityIntegerPair.getKey().resourceType,commodityIntegerPair.getValue()));
            }
            Trade trade = new Trade(resources, Game.currentGovernment,Game.getGovernmentByUser(selectedUser));

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("User Input");
            dialog.setHeaderText("Enter your message:");

            AtomicReference<String> message = new AtomicReference<>("");
            dialog.showAndWait().ifPresent(result -> {
                message.set(result.trim());
            });
            trade.setMessage(message.get());
            trade.setRequest(true);

            if (trade.toGovernment != null && trade.fromGovernment != null){
                trade.toGovernment.addTrade(trade);
                trade.fromGovernment.addTrade(trade);
            }
        });

        hBox.getChildren().addAll(donate,request);
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.CENTER);

        Button back = new Button("Back");
        back.setMinWidth(280);
        back.setOnAction(actionEvent -> {
            TradeGUI tradeGUI = new TradeGUI();
            try {
                tradeGUI.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        donate.setMinWidth(140);
        request.setMinWidth(140);
        root.getChildren().addAll(hBox,back);
    }

    public void updateCommodityList(ArrayList<Pair<Commodity,Integer>> commodities, Label label){
        String text = "Items to trade: ";
        for (Pair<Commodity,Integer> commodityIntegerPair:commodities){
            text += commodityIntegerPair.getKey().resourceType.name + " " + commodityIntegerPair.getValue()+"/ ";
        }
        label.setText(text);

    }
}
