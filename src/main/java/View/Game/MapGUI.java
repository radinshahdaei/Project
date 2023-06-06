package View.Game;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.plaf.TableHeaderUI;
import java.util.ArrayList;

public class MapGUI extends Application {
    private static double startingX = 0;
    private static double startingY = 0;
    private static double xStamp = 0;
    private static double yStamp = 0;
    private static int mapSize = 200;
    private static Pane[][] map = new Pane[mapSize][mapSize];
    private static ArrayList<Pane> selectedTiles = new ArrayList<>();
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Pane gamePane = new Pane();
        gamePane.setPrefSize(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        Scene scene = new Scene(gamePane);
        createMap();

        drawMap(startingX, startingY, gamePane, scene);
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
                    x = Math.min(160 * mapSize - gamePane.getWidth(), x);
                    y = Math.min(160 * mapSize - gamePane.getHeight(), y);
                    startingX = x;
                    startingY = y;
                    gamePane.getChildren().clear();
                    drawMap(startingX, startingY, gamePane, scene);
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
                    x = Math.min(160 * mapSize - gamePane.getWidth(), x);
                    startingX = x;
                }
                if (event.getCode().getName().equals("Up")) {
                    double y = startingY - 70;
                    y = Math.max(0, y);
                    startingY = y;
                }
                if (event.getCode().getName().equals("Down")) {
                    double y = startingY + 70;
                    y = Math.min(160 * mapSize - gamePane.getHeight(), y);
                    startingY = y;
                }
                gamePane.getChildren().clear();
                drawMap(startingX, startingY, gamePane, scene);
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

    private void drawMap(double startingX, double startingY, Pane gamePane, Scene scene) {
        int x = (int) (startingX / 160);
        int y = (int) (startingY / 160);
        for (int i = 0 ; i < 16 ; i++) {
            for (int j = 0 ; j < 10 ; j++) {
                map[x + i][y + j].setLayoutX((x + i) * 160 - startingX - 10);
                map[x + i][y + j].setLayoutY((y + j) * 160 - startingY - 10);
                gamePane.getChildren().add(map[x + i][y + j]);
            }
        }
    }
}
