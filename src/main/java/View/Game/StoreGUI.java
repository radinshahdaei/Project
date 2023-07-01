package View.Game;

import Controller.StoreMenuController;
import Model.Building.Storage.Storage;
import Model.Commodity;
import Model.Game;
import Model.Resources.ResourceType;
import Model.Store;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class StoreGUI extends Application {
    public static StoreGUI instance = null;
    @Override
    public void start(Stage stage) throws Exception {
        stage.setOnCloseRequest(event -> instance = null);
        VBox root = new VBox();
        root.setSpacing(5);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 300, 400);
        stage.setScene(scene);
        stage.setTitle("Store");
        stage.show();

        AtomicReference<Commodity> commodityToAction = new AtomicReference<>();

        AtomicInteger goldAmount = new AtomicInteger();
        AtomicReference<Storage> stockpile = new AtomicReference<>();
        if (Game.currentGovernment != null) stockpile.set((Storage) Game.currentGovernment.findBuildingByName("stockpile"));
        if (stockpile.get() != null) goldAmount.set(stockpile.get().getStoredResourceByType(ResourceType.GOLD).getCount());
        else {
            goldAmount.set(0);
        }

        Label label = new Label("Selected commodity:\t\t\tgold: "+goldAmount);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);

        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setSpacing(5);

        ArrayList<BorderPane> commodities = new ArrayList<>();

        javafx.scene.control.Button buy = new Button("Buy 5 for 20 gold");
        buy.setMinWidth(140);
        buy.setOnAction(actionEvent -> {
            boolean result = false;
            if (Game.currentGovernment != null) showSuccess(result = StoreMenuController.buy(commodityToAction.get().resourceType.name, 5),"buy");
            if (result){
                for (BorderPane borderPane:commodities) {
                    if (commodityToAction.get().resourceType.name.equals("\t"+((Text) borderPane.getLeft()).getText())) {
                        ((Text) borderPane.getCenter()).setText(String.valueOf(Integer.parseInt(((Text) borderPane.getCenter()).getText()) + 5));
                    }
                }
                if (Game.currentGovernment != null) stockpile.set((Storage) Game.currentGovernment.findBuildingByName("stockpile"));
                if (stockpile.get() != null) goldAmount.set(stockpile.get().getStoredResourceByType(ResourceType.GOLD).getCount());
                else {
                    goldAmount.set(0);
                }
                if (commodityToAction.get() != null) label.setText("Selected commodity: "+ commodityToAction.get().resourceType.name + "\t\tGold: "+goldAmount);
            }
        });

        Button sell = new Button("Sell 5 for 10 gold");
        sell.setMinWidth(140);
        sell.setOnAction(actionEvent -> {
            boolean result = false;
            if (Game.currentGovernment != null) showSuccess(result = StoreMenuController.sell(commodityToAction.get().resourceType.name,5),"sell");
            if (result){
                for (BorderPane borderPane:commodities) {
                    if (commodityToAction.get().resourceType.name.equals("\t"+((Text) borderPane.getLeft()).getText())) {
                        ((Text) borderPane.getCenter()).setText(String.valueOf(Integer.parseInt(((Text) borderPane.getCenter()).getText()) - 5));
                    }
                }
            }
            if (Game.currentGovernment != null) stockpile.set((Storage) Game.currentGovernment.findBuildingByName("stockpile"));
            if (stockpile.get() != null) goldAmount.set(stockpile.get().getStoredResourceByType(ResourceType.GOLD).getCount());
            else {
                goldAmount.set(0);
            }
            if (commodityToAction.get() != null) label.setText("Selected commodity: "+ commodityToAction.get().resourceType.name + "\t\tGold: "+goldAmount);
        });

        VBox vbox = new VBox();
        Store.initializeCommodities();

        for (Commodity commodity:Store.commodities){
            BorderPane borderPane = new BorderPane();
            Text text1 = new Text("   " + commodity.resourceType.name);
            Text text2 = new Text("Stock: "+commodity.stock);
            text1.setFont(Font.font(15));
            text2.setFont(Font.font(15));
            javafx.scene.control.Button button = new javafx.scene.control.Button("Select");
            button.setOnAction(actionEvent -> {
                label.setText("Selected commodity: "+commodity.resourceType.name + "\t\tGold: "+goldAmount);
                commodityToAction.set(commodity);
            });
            borderPane.setLeft(text1);
            borderPane.setCenter(text2);
            borderPane.setRight(button);
            borderPane.setMinWidth(280);

            borderPane.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");
            vbox.getChildren().add(borderPane);
            commodities.add(borderPane);
        }
        vbox.setAlignment(Pos.CENTER);
        javafx.scene.control.ScrollPane scrollPane = new javafx.scene.control.ScrollPane();
        scrollPane.setContent(vbox);
        scrollPane.setMinWidth(300);

        scrollPane.setMaxHeight(300);
        root.getChildren().add(scrollPane);
        root.getChildren().add(label);
        hBox.getChildren().addAll(buy,sell);
        root.getChildren().add(hBox);

        Button goToTrade = new Button("Trade Menu");
        goToTrade.setOnAction(actionEvent -> {
            TradeGUI tradeGUI = new TradeGUI();
            try {
                tradeGUI.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        goToTrade.setMinWidth(280);
        root.getChildren().add(goToTrade);
    }

    public void showSuccess(boolean success,String type){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (success) {
            alert.setHeaderText(type + "successful");
        } else {
            if (type.equals("sell")) alert.setHeaderText("selling failed, not enough in stock!");
            else alert.setHeaderText("buying failed, not enough gold!");
        }
        alert.showAndWait();
    }
}
