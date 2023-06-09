package View.Game;

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
import java.util.HashMap;

public class MapGUI extends Application {
    private static double startingX = 0;
    private static double startingY = 0;
    private static double xStamp = 0;
    private static double yStamp = 0;
    private static int mapSize = 200;
    private static Pane[][] map = new Pane[mapSize][mapSize];
    private static ArrayList<Pane> selectedTiles = new ArrayList<>();
    private static int scale = 160;
    public static HashMap<Node, Double> firstX = new HashMap<>();
    public static HashMap<Node, Double> firstY = new HashMap<>();
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Pane gamePane = new Pane();
        gamePane.setPrefSize(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        Scene scene = new Scene(gamePane);
        Pane menuPane = new Pane();
        menuPane.setPrefSize(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight() / 4);
        menuPane.setLayoutX(0);
        menuPane.setLayoutY(3 * Screen.getPrimary().getBounds().getHeight() / 4);
        menuPane.setStyle("-fx-background-color: white");
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
                map[i][j] = new Pane();
                map[i][j].setPrefSize(170, 170);
                Rectangle backGroundRectangle = new Rectangle(0, 0, 170, 170);
                backGroundRectangle.setFill(Color.TRANSPARENT);
                Rectangle rect = new Rectangle(10, 10, 150, 150);
                rect.setFill(Color.BLUE);
                map[i][j].getChildren().add(backGroundRectangle);
                map[i][j].getChildren().add(rect);
                firstX.put(backGroundRectangle, (double) 0);
                firstY.put(backGroundRectangle, (double) 0);
                firstX.put(rect, (double) 10);
                firstY.put(rect, (double) 10);
                int I = i;
                int J = j;
                map[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        selectedTile(map[I][J]);
                    }
                });

            }
        }
    }

    private void selectedTile(Pane tile){
        Rectangle rectangle = (Rectangle) tile.getChildren().get(0);
        if (rectangle.getFill() == Color.RED) {
            selectedTiles.remove(tile);
            rectangle.setFill(Color.TRANSPARENT);
        }
        else {
            selectedTiles.add(tile);
            rectangle.setFill(Color.RED);
        }
    }

    private void drawMap(double startingX, double startingY, Pane gamePane, Pane menuPane) {
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
    }
}
