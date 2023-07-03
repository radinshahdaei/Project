package Client.View.Game;

import Client.Controller.GameMenuController;
import Client.Model.Game;
import Client.Model.Tile;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;


public class MenusGUI {
    private Pane menuPane;

    public MenusGUI(Pane menuPane) {
        this.menuPane = menuPane;
    }

    public void createGUI() {
        Button goToBuildingMenu = new Button();
        goToBuildingMenu.setMinWidth(120);
        goToBuildingMenu.setLayoutX(10);
        goToBuildingMenu.setLayoutY(10);
        goToBuildingMenu.setText("Building Menu");
        goToBuildingMenu.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MapGUI.buildingMenuGUI.runMenu();
            }
        });
        menuPane.getChildren().add(goToBuildingMenu);

        Button goToGovernmentMenu = new Button();
        goToGovernmentMenu.setMinWidth(120);
        goToGovernmentMenu.setLayoutX(10);
        goToGovernmentMenu.setLayoutY(50);
        goToGovernmentMenu.setText("Government Menu");
        goToGovernmentMenu.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MapGUI.governmentMenuGUI.runMenu();
            }
        });
        menuPane.getChildren().add(goToGovernmentMenu);

        Button goToStore = new Button();
        goToStore.setMinWidth(120);
        goToStore.setLayoutX(10);
        goToStore.setLayoutY(90);
        goToStore.setText("Store");
        goToStore.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (StoreGUI.instance == null){
                    StoreGUI storeGUI = new StoreGUI();
                    StoreGUI.instance = storeGUI;
                    try {
                        storeGUI.start(new Stage());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        menuPane.getChildren().add(goToStore);

        Button endGame = new Button();
        endGame.setMinWidth(120);
        endGame.setLayoutX(10);
        endGame.setLayoutY(130);
        endGame.setText("End the game");
        endGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    MapGUI.endGame();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        menuPane.getChildren().add(endGame);

        Button goToDropUnitMenu = new Button();
        goToDropUnitMenu.setMinWidth(120);
        goToDropUnitMenu.setText("DropUnit Menu");
        goToDropUnitMenu.setLayoutX(170);
        goToDropUnitMenu.setLayoutY(10);
        goToDropUnitMenu.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MapGUI.dropUnitMenuGUI.run();
            }
        });
        menuPane.getChildren().add(goToDropUnitMenu);

        Button goToResourceViewMenu = new Button();
        goToResourceViewMenu.setMinWidth(120);
        goToResourceViewMenu.setText("View resources");
        goToResourceViewMenu.setLayoutX(170);
        goToResourceViewMenu.setLayoutY(50);
        goToResourceViewMenu.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MapGUI.resourceViewGUI.run();
            }
        });
        menuPane.getChildren().add(goToResourceViewMenu);

        Button nextTurn = new Button();
        nextTurn.setMinWidth(120);
        nextTurn.setText("Next turn");
        nextTurn.setLayoutX(170);
        nextTurn.setLayoutY(90);
        nextTurn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                GameMenuController.nextTurn();
                MapGUI.tileDataThread.setRunner(false);
                MapGUI.tileDataThread.stop();
                MapGUI.setScale(160);
                MapGUI.setSelectedTiles(new ArrayList<>());
                MapGUI.governmentPlayingThread.setRunner(false);
                MapGUI.setFirstTime(false);
                MapGUI mapGUI = new MapGUI();
                try {
                    mapGUI.start(MapGUI.getMyStage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        menuPane.getChildren().add(nextTurn);

        Text currentPlayer = new Text();
        currentPlayer.setText("Current government: " + Game.currentGovernment.getUser().getUsername());
        currentPlayer.setLayoutX(1000);
        currentPlayer.setLayoutY(30);
        currentPlayer.setScaleX(1.3);
        currentPlayer.setScaleY(1.3);
        menuPane.getChildren().add(currentPlayer);

        createMiniMap();
    }

    private void createMiniMap() {
//        Rectangle miniMap = new Rectangle(1285, 0 ,250, 250);
        Pane miniMap = new Pane();
        miniMap.setLayoutX(1285);
        miniMap.setLayoutY(0);
        miniMap.setPrefSize(250 ,250);
        menuPane.getChildren().add(miniMap);
        double dim = (double) 250 / GameMenuController.mapSize;
        Tile[][] tiles = GameMenuController.game.getMap().getTiles();
        for (int i = 0 ; i < GameMenuController.mapSize ; i+=10) {
            for (int j = 0; j < GameMenuController.mapSize; j+=10) {
                ImageView imageView = new ImageView(new Image(Tile.class.getResource("/Images/Textures/" +
                        tiles[i][j].getTexture().toLowerCase() + ".png").toString()));
                imageView.setFitHeight(dim * 10);
                imageView.setFitWidth(dim * 10);
                imageView.setLayoutX(i * dim);
                imageView.setLayoutY(j * dim);
                miniMap.getChildren().add(imageView);
            }
        }

        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.LIGHTBLUE);colors.add(Color.RED);colors.add(Color.BLACK);colors.add(Color.GOLD);colors.add(Color.PURPLE);
        ArrayList<Integer> Xs = new ArrayList<>();
        Xs.add(10);Xs.add(240);Xs.add(10);Xs.add(240);Xs.add(125);
        ArrayList<Integer> Ys = new ArrayList<>();
        Ys.add(10);Ys.add(10);Ys.add(200);Ys.add(200);Ys.add(125);
        for (int i = 0 ; i < GameMenuController.game.getGovernments().size() ; i++) {
            Circle circle = new Circle(5);
            circle.setFill(colors.get(i % 6));
            circle.setLayoutX(Xs.get(i % 6));
            circle.setLayoutY(Ys.get(i % 6));
            miniMap.getChildren().add(circle);
        }
    }

    public void runMenu() {
        menuPane.getChildren().clear();
        createGUI();
    }

    public Pane getMenuPane() {
        return menuPane;
    }

    public void setMenuPane(Pane menuPane) {
        this.menuPane = menuPane;
    }
}