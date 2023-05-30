package View.Game;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MapMenuGUI extends Application {
    private static double xStamp = 0;
    private static double yStamp = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane contentPane = new Pane();
        contentPane.setPrefSize(160 * 100, 160 * 100);
        for (int i = 0 ; i < 2 ; i++) {
            for (int j = 0 ; j < 2 ; j++) {
                Rectangle rectangle = new Rectangle(i * 160, j * 160, 150 ,150);
                rectangle.setFill(Color.BLUE);
                contentPane.getChildren().add(rectangle);
            }
        }


        ScrollPane scrollPane = new ScrollPane(contentPane);
        scrollPane.setPrefSize(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent;");
        contentPane.setOnScroll((ScrollEvent event) -> {
            double deltaY = event.getDeltaY();
            double deltaX = event.getDeltaX();
            double contentWidth = contentPane.getBoundsInLocal().getWidth();
            double contentHeight = contentPane.getBoundsInLocal().getHeight();
            double viewportWidth = event.getX();
            double viewportHeight = event.getY();
            if (Math.abs(viewportWidth - xStamp) < 100 && Math.abs(viewportHeight - yStamp) < 100) return;
            yStamp = viewportHeight;
            xStamp = viewportWidth;
            contentPane.getChildren().clear();
            for (int i = 0 ; i < 40 ; i++) {
                for (int j = 0 ; j < 40 ; j++) {
                    Rectangle rectangle = new Rectangle((i + (int)viewportWidth - 20) * 160, (j + (int)viewportHeight - 20) * 160, 150 ,150);
                    rectangle.setFill(Color.BLUE);
                    contentPane.getChildren().add(rectangle);
                }
            }

        });
        Scene scene = new Scene(scrollPane);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
