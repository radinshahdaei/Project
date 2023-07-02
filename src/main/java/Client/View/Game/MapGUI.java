package Client.View.Game;

import Client.Controller.TileDataThread;
import Client.Model.Tile;
import Client.View.Start.StartMenuGUI;
import Client.Controller.BuildingMenuController;
import Client.Controller.GameMenuController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MapGUI extends Application {
    private static double startingX = 0;
    private static double startingY = 0;
    private static double xStamp = 0;
    private static double yStamp = 0;
    private static int mapSize = GameMenuController.mapSize;
    private static Pane[][] map = new Pane[mapSize][mapSize];
    private static Pane[][] dataPanes = new Pane[mapSize][mapSize];
    private static Pane gamePane;
    private static Pane menuPane;
    public static MenusGUI menusGUI;
    public static BuildingMenuGUI buildingMenuGUI;
    public static GovernmentMenuGUI governmentMenuGUI;
    public static DropUnitMenuGUI dropUnitMenuGUI;
    public static ResourceViewGUI resourceViewGUI;
    public static UnitMenuGUI unitMenuGUI;
    private static Pane tileDataPane;
    public static TileDataThread tileDataThread;
    private static Tile[][] tiles;
    private static ArrayList<Tile> selectedTiles = new ArrayList<>();
    private static int scale = 160;
    private static Stage myStage;
    public static GovernmentPlayingThread governmentPlayingThread;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        myStage = stage;
        tiles = GameMenuController.game.getMap().getTiles();
        gamePane = new Pane();
        gamePane.setPrefSize(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        Scene scene = new Scene(gamePane);
        menuPane = new Pane();
        menuPane.setPrefSize(4 * Screen.getPrimary().getBounds().getWidth() / 5, Screen.getPrimary().getBounds().getHeight() / 4);
        menuPane.setLayoutX(0);
        menuPane.setLayoutY(3 * Screen.getPrimary().getBounds().getHeight() / 4);
        menuPane.setStyle("-fx-background-color: white");
        tileDataPane = new Pane();
        tileDataPane.setPrefSize(Screen.getPrimary().getBounds().getWidth() / 5, Screen.getPrimary().getBounds().getHeight() / 4);
        tileDataPane.setLayoutX(4 * Screen.getPrimary().getBounds().getWidth() / 5);
        tileDataPane.setLayoutY(3 * Screen.getPrimary().getBounds().getHeight() / 4);
        tileDataPane.setStyle("-fx-background-color: wheat");
        menusGUI = new MenusGUI(menuPane);
        buildingMenuGUI = new BuildingMenuGUI(menuPane);
        governmentMenuGUI = new GovernmentMenuGUI(menuPane);
        dropUnitMenuGUI = new DropUnitMenuGUI(menuPane);
        resourceViewGUI = new ResourceViewGUI(menuPane);
        unitMenuGUI = new UnitMenuGUI(menuPane);
        menusGUI.runMenu();
        createMap();

        drawMap(startingX, startingY, gamePane, menuPane);
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xStamp = event.getX();
                yStamp = event.getY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!event.isControlDown()){
                    double x = (xStamp - event.getX()) + startingX;
                    double y = (yStamp - event.getY()) + startingY;
                    xStamp = event.getX();
                    yStamp = event.getY();
                    x = Math.max(0, x);
                    y = Math.max(0, y);
                    x = Math.min(scale * mapSize - gamePane.getWidth(), x);
                    y = Math.min(scale * mapSize - gamePane.getHeight() + menuPane.getHeight(), y);
                    startingX = x;
                    startingY = y;
                    gamePane.getChildren().clear();
                    drawMap(startingX, startingY, gamePane, menuPane);
                }
            }
        });

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.isShiftDown()) return;
                if (event.getCode().getName().equals("Left")) {
                    double x = startingX - 70;
                    x = Math.max(0, x);
                    startingX = x;
                }
                if (event.getCode().getName().equals("Right")) {
                    double x = startingX + 70;
                    x = Math.min(scale * mapSize - gamePane.getWidth(), x);
                    startingX = x;
                }
                if (event.getCode().getName().equals("Up")) {
                    double y = startingY - 70;
                    y = Math.max(0, y);
                    startingY = y;
                }
                if (event.getCode().getName().equals("Down")) {
                    double y = startingY + 70;
                    y = Math.min(scale * mapSize - gamePane.getHeight() + menuPane.getHeight(), y);
                    startingY = y;
                }
                gamePane.getChildren().clear();
                drawMap(startingX, startingY, gamePane, menuPane);
            }
        });

        scene.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaY() > 0) scale += 5;
                if (event.getDeltaY() < 0) scale -= 5;
                scale = Math.min(250, scale);
                scale = Math.max(120, scale);
                gamePane.getChildren().clear();
                drawMap(startingX, startingY, gamePane, menuPane);
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    private void createMap() {
        for (int i = 0 ; i < mapSize ; i++) {
            for (int j = 0 ; j < mapSize ; j++) {
                map[i][j] = GameMenuController.game.getMap().getTiles()[i][j].getMainPane();
                tiles[i][j].showOnPane();
                dataPanes[i][j] = GameMenuController.game.getMap().getTiles()[i][j].getDataPane();
                dataPanes[i][j].setPrefSize(Screen.getPrimary().getBounds().getWidth() / 5,
                        Screen.getPrimary().getBounds().getHeight() / 4);
                dataPanes[i][j].setLayoutX(0);
                dataPanes[i][j].setLayoutY(0);
                Rectangle dataBackGround = new Rectangle(0, 0,
                        Screen.getPrimary().getBounds().getWidth() / 5,
                        Screen.getPrimary().getBounds().getHeight() / 4);
                dataBackGround.setFill(Color.WHEAT);
                dataPanes[i][j].getChildren().add(dataBackGround);
                int I = i;
                int J = j;
                map[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        selectedTile(map[I][J], GameMenuController.game.getMap().getTiles()[I][J]);
                    }
                });

                map[i][j].setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (selectedTiles.size() != 0) return;
                        tileDataThread = new TileDataThread(GameMenuController.game.getMap().getTiles()[I][J]);
                        if (!tileDataPane.getChildren().contains(dataPanes[I][J])) {
                            tileDataPane.getChildren().add(dataPanes[I][J]);
                            tileDataThread.play();
                        }
                    }
                });

                map[i][j].setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (selectedTiles.size() != 0) return;
                        tileDataThread.setRunner(false);
                        tileDataThread.stop();
                        tileDataPane.getChildren().remove(dataPanes[I][J]);
                    }
                });

                map[i][j].setOnDragOver(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        if (event.getGestureSource() != map[I][J] &&
                                event.getDragboard().hasString()) {
                            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        }

                        event.consume();
                    }
                });

                map[i][j].setOnDragDropped(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        Dragboard db = event.getDragboard();
                        boolean success = false;
                        if (db.hasString()) {
                            BuildingMenuController.dropBuilding(I, J, BuildingMenuGUI.nameToBuildings.get(db.getString()));
                            tiles[I][J].showOnPane();
                            gamePane.getChildren().clear();
                            drawMap(startingX, startingY, gamePane, menuPane);
                            success = true;
                        }
                        event.setDropCompleted(success);

                        event.consume();
                    }
                });


            }
        }
    }

    private void selectedTile(Pane pane, Tile tile){
        Rectangle rectangle = (Rectangle) pane.getChildren().get(0);
        if (rectangle.getFill() == Color.RED) {
            if (tileDataThread.getTile().equals(tile)) {
                tileDataThread.setRunner(false);
                tileDataThread.stop();
                tileDataPane.getChildren().remove(dataPanes[tile.getX()][tile.getY()]);
            }
            selectedTiles.remove(tile);
            rectangle.setFill(Color.TRANSPARENT);
        }
        else {
            selectedTiles.add(tile);
            rectangle.setFill(Color.RED);
            if (selectedTiles.size() == 1) {
                tileDataThread.stop();
                tileDataThread = new TileDataThread(tile);
                tileDataPane.getChildren().clear();
                tileDataPane.getChildren().add(dataPanes[tile.getX()][tile.getY()]);
                tileDataThread.play();
            }
        }
    }
    public static void fixMap() {
        gamePane.getChildren().clear();
        drawMap(startingX, startingY, gamePane, menuPane);
    }
    private static void drawMap(double startingX, double startingY, Pane gamePane, Pane menuPane) {
        int x = (int) (startingX / scale);
        int y = (int) (startingY / scale);
        for (int i = 0; i < 16 * 160 / scale; i++) {
            for (int j = 0; j < 10 * 160 / scale; j++) {
                if (x + i >= mapSize || y + j >= mapSize) break;
                map[x + i][y + j].setPrefSize(scale + (double) scale / 16, scale + (double) scale / 16);
                for (Node node: map[x + i][y + j].getChildren()) {
                    node.setScaleX((double) scale / 160);
                    node.setScaleY((double) scale / 160);
                }
                map[x + i][y + j].setLayoutX((x + i) * scale - startingX - (double) scale / 16);
                map[x + i][y + j].setLayoutY((y + j) * scale - startingY - (double) scale / 16);
                gamePane.getChildren().add(map[x + i][y + j]);
            }
        }
        gamePane.getChildren().add(menuPane);
        gamePane.getChildren().add(tileDataPane);
    }

    public static Tile[][] getTiles() {
        return tiles;
    }

    public static ArrayList<Tile> getSelectedTiles() {
        return selectedTiles;
    }

    public static void endGame() throws Exception {
        governmentPlayingThread.stop();
        tileDataThread.stop();
        StartMenuGUI startMenuGUI = new StartMenuGUI();
        startMenuGUI.start(myStage);
    }
}