package View.Game;

import Controller.GameMenuController;
import Controller.UnitMenuController;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import static View.InputOutput.output;

public class DropUnitMenuGUI {
    private Pane menuPane;

    public DropUnitMenuGUI(Pane menuPane) {
        this.menuPane = menuPane;
    }

    public void run() {
        menuPane.getChildren().clear();
        createGUI();
    }

    private void createGUI() {
        ImageView back = new ImageView(new Image(GovernmentMenuGUI.class.getResource("/MenuImages/back.png").toExternalForm()));
        back.setLayoutX(-200);
        back.setLayoutY(-200);
        back.setScaleX(0.1);
        back.setScaleY(0.1);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MapGUI.menusGUI.runMenu();
            }
        });
        menuPane.getChildren().add(back);

        VBox vBox = new VBox();
        vBox.setLayoutX(400);
        vBox.setLayoutY(100);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        menuPane.getChildren().add(vBox);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(15);
        vBox.getChildren().add(hBox);

        Text soldierNameText = new Text();
        soldierNameText.setText("Soldier name:");
        hBox.getChildren().add(soldierNameText);

        TextField soldierName = new TextField();
        soldierName.setMaxWidth(250);
        soldierName.setPromptText("soldier name");
        hBox.getChildren().add(soldierName);

        Text posXText = new Text();
        posXText.setText("X:");
        hBox.getChildren().add(posXText);

        TextField posX = new TextField();
        posX.setMaxWidth(50);
        hBox.getChildren().add(posX);

        Text posYText = new Text();
        posYText.setText("Y:");
        hBox.getChildren().add(posYText);

        TextField posY = new TextField();
        posY.setMaxWidth(50);
        hBox.getChildren().add(posY);

        Text countText = new Text();
        countText.setText("Count:");
        hBox.getChildren().add(countText);

        TextField count = new TextField();
        count.setMaxWidth(50);
        hBox.getChildren().add(count);

        Button submit = new Button();
        submit.setText("Submit");
        submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!posX.getText().matches("\\d+")) {
                    output("X should be a number", 'd');
                    return;
                }
                if (!posY.getText().matches("\\d+")) {
                    output("Y should be a number", 'd');
                    return;
                }
                if (!count.getText().matches("\\d+")) {
                    output("Count should be a number", 'd');
                    return;
                }
                UnitMenuController.dropUnit(Integer.parseInt(posX.getText()), Integer.parseInt(posY.getText()),
                        Integer.parseInt(count.getText()), soldierName.getText());
                GameMenuController.game.getMap().getTiles()[Integer.parseInt(posX.getText())]
                        [Integer.parseInt(posY.getText())].showOnPane();
            }
        });
        vBox.getChildren().add(submit);

    }
}
