package View.Game;

import Model.Tile;
import Controller.BuildingMenuController;
import javafx.event.EventHandler;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class BuildingMenuGUI {
    private Pane menuPane;
    private Pane dataPane;
    private String selected;
    public static HashMap<String, String> nameToBuildings;
    static {
        nameToBuildings = new HashMap<>();

        nameToBuildings.put("Stock pile", "stockpile");
        nameToBuildings.put("Armoury", "armoury");
        nameToBuildings.put("Granary", "granary");
        nameToBuildings.put("Stable", "stable");

        nameToBuildings.put("Barracks", "barracks");
        nameToBuildings.put("Mercenary post", "mercenary post");
        nameToBuildings.put("Engineer guild", "engineer guild");
        nameToBuildings.put("Tunneler guild", "tunneler guild");

        nameToBuildings.put("Lookout tower", "lookout tower");
        nameToBuildings.put("Perimeter tower", "perimeter tower");
        nameToBuildings.put("Defence turret", "defence turret");
        nameToBuildings.put("Square tower", "square tower");
        nameToBuildings.put("Round tower", "round tower");

        nameToBuildings.put("Armourer", "armourer");
        nameToBuildings.put("Tanner", "tanner");
        nameToBuildings.put("Fletcher", "fletcher");
        nameToBuildings.put("Pole turner", "poleturner");
        nameToBuildings.put("Black smith", "blacksmith");

        nameToBuildings.put("Iron Mine", "iron mine");
        nameToBuildings.put("Quarry", "quarry");
        nameToBuildings.put("Wood Cutter", "wood cutter");
        nameToBuildings.put("Pitch Rig", "pitch rig");
        nameToBuildings.put("Apple orchard", "apple orchard");
        nameToBuildings.put("Hops farmer", "hops farmer");
        nameToBuildings.put("Hunter post", "hunter post");
        nameToBuildings.put("Diary farmer", "diary farmer");
        nameToBuildings.put("Wheat farmer", "wheat farmer");
        nameToBuildings.put("Mill", "mill");
        nameToBuildings.put("Bakery", "bakery");
        nameToBuildings.put("Brewer", "brewer");
        nameToBuildings.put("Inn", "inn");
    }

    public BuildingMenuGUI(Pane menuPane) {
        this.menuPane = menuPane;
        dataPane = new Pane();
        dataPane.setPrefSize(menuPane.getWidth(), menuPane.getHeight() - 30);
        dataPane.setLayoutX(0);
        dataPane.setLayoutY(30);
        dataPane.setStyle("-fx-background-color: white");
        selected = "Factory";
    }

    public void createGUI() {
        Text back = new Text();
        back.setText("Back");
        back.setLayoutX(20);
        back.setLayoutY(20);
        back.setScaleX(1.2);
        back.setScaleY(1.2);
        back.setFill(Color.RED);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MapGUI.menusGUI.runMenu();
            }
        });
        menuPane.getChildren().add(back);
        Text factory = new Text();
        factory.setText("Factories");
        factory.setFill(Color.BLACK);
        factory.setLayoutX(menuPane.getWidth() / 6);
        factory.setLayoutY(20);
        factory.setScaleX(1.3);
        factory.setScaleY(1.3);
        factory.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selected = "Factory";
                showSelectedBuildingType();
            }
        });
        menuPane.getChildren().add(factory);
        Text barracks = new Text();
        barracks.setText("Barracks");
        barracks.setFill(Color.BLACK);
        barracks.setLayoutX(2 * menuPane.getWidth() / 6);
        barracks.setLayoutY(20);
        barracks.setScaleX(1.3);
        barracks.setScaleY(1.3);
        barracks.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selected = "Barracks";
                showSelectedBuildingType();
            }
        });
        menuPane.getChildren().add(barracks);
        Text storage = new Text();
        storage.setText("Storage");
        storage.setFill(Color.BLACK);
        storage.setLayoutX(3 * menuPane.getWidth() / 6);
        storage.setLayoutY(20);
        storage.setScaleX(1.3);
        storage.setScaleY(1.3);
        storage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selected = "Storage";
                showSelectedBuildingType();
            }
        });
        menuPane.getChildren().add(storage);
        Text defensive = new Text();
        defensive.setText("Defensive");
        defensive.setFill(Color.BLACK);
        defensive.setLayoutX(4 * menuPane.getWidth() / 6);
        defensive.setLayoutY(20);
        defensive.setScaleX(1.3);
        defensive.setScaleY(1.3);
        defensive.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selected = "Defensive";
                showSelectedBuildingType();
            }
        });
        menuPane.getChildren().add(defensive);
        Text weapon = new Text();
        weapon.setText("Weapon maker");
        weapon.setFill(Color.BLACK);
        weapon.setLayoutX(5 * menuPane.getWidth() / 6);
        weapon.setLayoutY(20);
        weapon.setScaleX(1.3);
        weapon.setScaleY(1.3);
        weapon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selected = "Weapon";
                showSelectedBuildingType();
            }
        });
        menuPane.getChildren().add(weapon);
    }

    private void showSelectedBuildingType() {
        if (!menuPane.getChildren().contains(dataPane)) menuPane.getChildren().add(dataPane);
        dataPane.getChildren().clear();
        ArrayList<Tile> selectedTiles = MapGUI.getSelectedTiles();
        if (selected.equals("Factory")) {
            Text ironMine = new Text();
            ironMine.setText("Iron Mine");
            ironMine.setFill(Color.GREEN);
            ironMine.setLayoutX(menuPane.getWidth() / 6);
            ironMine.setLayoutY(40);
            ironMine.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "iron mine");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            ironMine.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = ironMine.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(ironMine.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(ironMine);

            Text quarry = new Text();
            quarry.setText("Quarry");
            quarry.setFill(Color.GREEN);
            quarry.setLayoutX(2 * menuPane.getWidth() / 6);
            quarry.setLayoutY(40);
            quarry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "quarry");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            quarry.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = quarry.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(quarry.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(quarry);

            Text woodCutter = new Text();
            woodCutter.setText("Wood Cutter");
            woodCutter.setFill(Color.GREEN);
            woodCutter.setLayoutX(3 * menuPane.getWidth() / 6);
            woodCutter.setLayoutY(40);
            woodCutter.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "wood cutter");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            woodCutter.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = woodCutter.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(woodCutter.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(woodCutter);

            Text pitchRig = new Text();
            pitchRig.setText("Pitch Rig");
            pitchRig.setFill(Color.GREEN);
            pitchRig.setLayoutX(4 * menuPane.getWidth() / 6);
            pitchRig.setLayoutY(40);
            pitchRig.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "pitch rig");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            pitchRig.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = pitchRig.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(pitchRig.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(pitchRig);

            Text appleOrchard = new Text();
            appleOrchard.setText("Apple orchard");
            appleOrchard.setFill(Color.GREEN);
            appleOrchard.setLayoutX(5 * menuPane.getWidth() / 6);
            appleOrchard.setLayoutY(40);
            appleOrchard.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "apple orchard");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            appleOrchard.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = appleOrchard.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(appleOrchard.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(appleOrchard);

            Text hopsFarmer = new Text();
            hopsFarmer.setText("Hops farmer");
            hopsFarmer.setFill(Color.GREEN);
            hopsFarmer.setLayoutX(menuPane.getWidth() / 6);
            hopsFarmer.setLayoutY(80);
            hopsFarmer.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "hops farmer");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            hopsFarmer.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = hopsFarmer.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(hopsFarmer.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(hopsFarmer);

            Text hunterPost = new Text();
            hunterPost.setText("Hunter post");
            hunterPost.setFill(Color.GREEN);
            hunterPost.setLayoutX(2 * menuPane.getWidth() / 6);
            hunterPost.setLayoutY(80);
            hunterPost.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "hunter post");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            hunterPost.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = hunterPost.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(hunterPost.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(hunterPost);

            Text diaryFarmer = new Text();
            diaryFarmer.setText("Diary farmer");
            diaryFarmer.setFill(Color.GREEN);
            diaryFarmer.setLayoutX(3 * menuPane.getWidth() / 6);
            diaryFarmer.setLayoutY(80);
            diaryFarmer.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "diary farmer");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            diaryFarmer.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = diaryFarmer.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(diaryFarmer.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(diaryFarmer);

            Text wheatFarmer = new Text();
            wheatFarmer.setText("Wheat farmer");
            wheatFarmer.setFill(Color.GREEN);
            wheatFarmer.setLayoutX(4 * menuPane.getWidth() / 6);
            wheatFarmer.setLayoutY(80);
            wheatFarmer.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "wheat farmer");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            wheatFarmer.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = wheatFarmer.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(wheatFarmer.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(wheatFarmer);

            Text mill = new Text();
            mill.setText("Mill");
            mill.setFill(Color.GREEN);
            mill.setLayoutX(5 * menuPane.getWidth() / 6);
            mill.setLayoutY(80);
            mill.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "mill");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            mill.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = mill.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(mill.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(mill);

            Text bakery = new Text();
            bakery.setText("Bakery");
            bakery.setFill(Color.GREEN);
            bakery.setLayoutX(menuPane.getWidth() / 6);
            bakery.setLayoutY(120);
            bakery.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "bakery");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            bakery.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = bakery.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(bakery.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(bakery);

            Text brewer = new Text();
            brewer.setText("Brewer");
            brewer.setFill(Color.GREEN);
            brewer.setLayoutX(2 * menuPane.getWidth() / 6);
            brewer.setLayoutY(120);
            brewer.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "brewer");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            brewer.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = brewer.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(brewer.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(brewer);

            Text inn = new Text();
            inn.setText("Inn");
            inn.setFill(Color.GREEN);
            inn.setLayoutX(3 * menuPane.getWidth() / 6);
            inn.setLayoutY(120);
            inn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "inn");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            inn.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = inn.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(inn.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(inn);
        }
        else if (selected.equals("Weapon")) {
            Text armourer = new Text();
            armourer.setText("Armourer");
            armourer.setFill(Color.GREEN);
            armourer.setLayoutX(menuPane.getWidth() / 6);
            armourer.setLayoutY(40);
            armourer.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "armourer");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            armourer.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = armourer.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(armourer.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(armourer);

            Text tanner = new Text();
            tanner.setText("Tanner");
            tanner.setFill(Color.GREEN);
            tanner.setLayoutX(2 * menuPane.getWidth() / 6);
            tanner.setLayoutY(40);
            tanner.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "tanner");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            tanner.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = tanner.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(tanner.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(tanner);

            Text fletcher = new Text();
            fletcher.setText("Fletcher");
            fletcher.setFill(Color.GREEN);
            fletcher.setLayoutX(3 * menuPane.getWidth() / 6);
            fletcher.setLayoutY(40);
            fletcher.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "fletcher");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            fletcher.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = fletcher.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(fletcher.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(fletcher);

            Text poleTurner = new Text();
            poleTurner.setText("Pole turner");
            poleTurner.setFill(Color.GREEN);
            poleTurner.setLayoutX(4 * menuPane.getWidth() / 6);
            poleTurner.setLayoutY(40);
            poleTurner.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "poleturner");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            poleTurner.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = poleTurner.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(poleTurner.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(poleTurner);

            Text blackSmith = new Text();
            blackSmith.setText("Black smith");
            blackSmith.setFill(Color.GREEN);
            blackSmith.setLayoutX(5 * menuPane.getWidth() / 6);
            blackSmith.setLayoutY(40);
            blackSmith.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "blacksmith");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            blackSmith.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = blackSmith.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(blackSmith.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(blackSmith);
        }
        else if (selected.equals("Defensive")) {
            Text lookoutTower = new Text();
            lookoutTower.setText("Lookout tower");
            lookoutTower.setFill(Color.GREEN);
            lookoutTower.setLayoutX(menuPane.getWidth() / 6);
            lookoutTower.setLayoutY(40);
            lookoutTower.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "lookout tower");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            lookoutTower.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = lookoutTower.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(lookoutTower.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(lookoutTower);

            Text perimeterTower = new Text();
            perimeterTower.setText("Perimeter tower");
            perimeterTower.setFill(Color.GREEN);
            perimeterTower.setLayoutX(2 * menuPane.getWidth() / 6);
            perimeterTower.setLayoutY(40);
            perimeterTower.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "perimeter tower");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            perimeterTower.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = perimeterTower.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(perimeterTower.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(perimeterTower);

            Text defenceTurret = new Text();
            defenceTurret.setText("Defence turret");
            defenceTurret.setFill(Color.GREEN);
            defenceTurret.setLayoutX(3 * menuPane.getWidth() / 6);
            defenceTurret.setLayoutY(40);
            defenceTurret.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "defence turret");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            defenceTurret.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = defenceTurret.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(defenceTurret.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(defenceTurret);

            Text squareTower = new Text();
            squareTower.setText("Square tower");
            squareTower.setFill(Color.GREEN);
            squareTower.setLayoutX(4 * menuPane.getWidth() / 6);
            squareTower.setLayoutY(40);
            squareTower.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "square tower");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            squareTower.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = squareTower.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(squareTower.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(squareTower);

            Text roundTower = new Text();
            roundTower.setText("Round tower");
            roundTower.setFill(Color.GREEN);
            roundTower.setLayoutX(5 * menuPane.getWidth() / 6);
            roundTower.setLayoutY(40);
            roundTower.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "round tower");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            roundTower.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = roundTower.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(roundTower.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(roundTower);
        }
        else if (selected.equals("Storage")) {
            Text stockPile = new Text();
            stockPile.setText("Stock pile");
            stockPile.setFill(Color.GREEN);
            stockPile.setLayoutX(menuPane.getWidth() / 6);
            stockPile.setLayoutY(40);
            stockPile.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "stockpile");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            stockPile.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = stockPile.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(stockPile.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(stockPile);

            Text armoury = new Text();
            armoury.setText("Armoury");
            armoury.setFill(Color.GREEN);
            armoury.setLayoutX(2 * menuPane.getWidth() / 6);
            armoury.setLayoutY(40);
            armoury.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "armoury");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            armoury.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = armoury.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(armoury.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(armoury);

            Text granary = new Text();
            granary.setText("Granary");
            granary.setFill(Color.GREEN);
            granary.setLayoutX(3 * menuPane.getWidth() / 6);
            granary.setLayoutY(40);
            granary.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "granary");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            granary.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = granary.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(granary.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(granary);

            Text stable = new Text();
            stable.setText("Stable");
            stable.setFill(Color.GREEN);
            stable.setLayoutX(4 * menuPane.getWidth() / 6);
            stable.setLayoutY(40);
            stable.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "stable");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            stable.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = stable.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(stable.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(stable);
        }
        else if (selected.equals("Barracks")) {
            Text barracks = new Text();
            barracks.setText("Barracks");
            barracks.setFill(Color.GREEN);
            barracks.setLayoutX(menuPane.getWidth() / 6);
            barracks.setLayoutY(40);
            barracks.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "barracks");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            barracks.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = barracks.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(barracks.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(barracks);

            Text mercenaryPost = new Text();
            mercenaryPost.setText("Mercenary post");
            mercenaryPost.setFill(Color.GREEN);
            mercenaryPost.setLayoutX(2 * menuPane.getWidth() / 6);
            mercenaryPost.setLayoutY(40);
            mercenaryPost.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "mercenary post");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            mercenaryPost.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = mercenaryPost.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(mercenaryPost.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(mercenaryPost);

            Text engineerGuild = new Text();
            engineerGuild.setText("Engineer guild");
            engineerGuild.setFill(Color.GREEN);
            engineerGuild.setLayoutX(3 * menuPane.getWidth() / 6);
            engineerGuild.setLayoutY(40);
            engineerGuild.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "engineer guild");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            engineerGuild.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = engineerGuild.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(engineerGuild.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(engineerGuild);

            Text tunnelerGuild = new Text();
            tunnelerGuild.setText("Tunneler guild");
            tunnelerGuild.setFill(Color.GREEN);
            tunnelerGuild.setLayoutX(4 * menuPane.getWidth() / 6);
            tunnelerGuild.setLayoutY(40);
            tunnelerGuild.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Tile tile: selectedTiles) {
                        BuildingMenuController.dropBuilding(tile.getX(), tile.getY(), "tunneler guild");
                        tile.showOnPane();  MapGUI.fixMap();
                    }
                }
            });
            tunnelerGuild.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isControlDown()){
                        Dragboard db = tunnelerGuild.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(tunnelerGuild.getText());
                        db.setContent(content);

                        event.consume();
                    }
                }
            });
            dataPane.getChildren().add(tunnelerGuild);
        }
    }

    public void runMenu() {
        menuPane.getChildren().clear();
        createGUI();
        showSelectedBuildingType();
    }

    public Pane getMenuPane() {
        return menuPane;
    }

    public void setMenuPane(Pane menuPane) {
        this.menuPane = menuPane;
    }

}
