package Client.View.Game;

import Client.Controller.UnitMenuController;
import Client.View.InputOutput;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class UnitMenuGUI {
    private Pane menuPane;
    private int x;
    private int y;
    private String type;

    public UnitMenuGUI(Pane menuPane) {
        this.menuPane = menuPane;
    }

    public void run() {
        menuPane.getChildren().clear();
        UnitMenuController.selectUnit(x, y, type);
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

        Text numberOfSelected = new Text();
        numberOfSelected.setLayoutX(menuPane.getWidth() - 200);
        numberOfSelected.setLayoutY(30);
        numberOfSelected.setText("Number of selected units: " + String.valueOf(UnitMenu.userUnitInTile.size()));
        menuPane.getChildren().add(numberOfSelected);

        moveUnitPart();

        setStatusPart();

        attackPart();

        attackArcherParts();
    }

    private void moveUnitPart() {
        HBox moveUnitsHBox = new HBox();
        moveUnitsHBox.setAlignment(Pos.CENTER);
        moveUnitsHBox.setSpacing(15);
        moveUnitsHBox.setLayoutX(150);
        moveUnitsHBox.setLayoutY(30);
        menuPane.getChildren().add(moveUnitsHBox);

        Text moveUnit = new Text();
        moveUnit.setText("Move units: ");
        moveUnitsHBox.getChildren().add(moveUnit);
        Text posXText = new Text();
        posXText.setText("X:");
        moveUnitsHBox.getChildren().add(posXText);
        TextField posX = new TextField();
        posX.setMaxWidth(50);
        moveUnitsHBox.getChildren().add(posX);
        Text posYText = new Text();
        posYText.setText("Y:");
        moveUnitsHBox.getChildren().add(posYText);
        TextField posY = new TextField();
        posY.setMaxWidth(50);
        moveUnitsHBox.getChildren().add(posY);
        Button move = new Button();
        move.setText("Move");
        move.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!posX.getText().matches("\\d+")) {
                    InputOutput.output("X should be a number", 'f');
                    return;
                }
                if (!posY.getText().matches("\\d+")) {
                    InputOutput.output("Y should be a number", 'f');
                    return;
                }
                UnitMenuController.moveUnit(Integer.parseInt(posX.getText()), Integer.parseInt(posY.getText()));
            }
        });
        moveUnitsHBox.getChildren().add(move);
    }

    private void setStatusPart() {
        HBox setStatusHBox = new HBox();
        setStatusHBox.setAlignment(Pos.CENTER);
        setStatusHBox.setSpacing(15);
        setStatusHBox.setLayoutX(150);
        setStatusHBox.setLayoutY(65);
        menuPane.getChildren().add(setStatusHBox);

        Text setStatusText = new Text();
        setStatusText.setText("Set status:");
        setStatusHBox.getChildren().add(setStatusText);
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().add("standing");
        choiceBox.getItems().add("defensive");
        choiceBox.getItems().add("offensive");
        choiceBox.getSelectionModel().select(UnitMenu.userUnitInTile.get(0).getStatus());
        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            UnitMenuController.setStatus(newValue);
        });
        setStatusHBox.getChildren().add(choiceBox);
    }

    private void attackPart() {
        HBox attackHBox = new HBox();
        attackHBox.setAlignment(Pos.CENTER);
        attackHBox.setSpacing(15);
        attackHBox.setLayoutX(150);
        attackHBox.setLayoutY(100);
        menuPane.getChildren().add(attackHBox);

        Text attackText = new Text();
        attackText.setText("Attack: ");
        attackHBox.getChildren().add(attackText);
        Text posXText1 = new Text();
        posXText1.setText("X:");
        attackHBox.getChildren().add(posXText1);
        TextField posX1 = new TextField();
        posX1.setMaxWidth(50);
        attackHBox.getChildren().add(posX1);
        Text posYText1 = new Text();
        posYText1.setText("Y:");
        attackHBox.getChildren().add(posYText1);
        TextField posY1 = new TextField();
        posY1.setMaxWidth(50);
        attackHBox.getChildren().add(posY1);
        Button attack = new Button();
        attack.setText("Attack");
        attack.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!posX1.getText().matches("\\d+")) {
                    InputOutput.output("X should be a number", 'f');
                    return;
                }
                if (!posY1.getText().matches("\\d+")) {
                    InputOutput.output("Y should be a number", 'f');
                    return;
                }
                UnitMenuController.attackEnemy(Integer.parseInt(posX1.getText()), Integer.parseInt(posY1.getText()));
            }
        });
        attackHBox.getChildren().add(attack);
    }

    private void attackArcherParts() {
        HBox attackArcherHBox = new HBox();
        attackArcherHBox.setAlignment(Pos.CENTER);
        attackArcherHBox.setSpacing(15);
        attackArcherHBox.setLayoutX(150);
        attackArcherHBox.setLayoutY(135);
        menuPane.getChildren().add(attackArcherHBox);

        Text attackArcherText = new Text();
        attackArcherText.setText("Attack archer: ");
        attackArcherHBox.getChildren().add(attackArcherText);
        Text posXText2 = new Text();
        posXText2.setText("X:");
        attackArcherHBox.getChildren().add(posXText2);
        TextField posX2 = new TextField();
        posX2.setMaxWidth(50);
        attackArcherHBox.getChildren().add(posX2);
        Text posYText2 = new Text();
        posYText2.setText("Y:");
        attackArcherHBox.getChildren().add(posYText2);
        TextField posY2 = new TextField();
        posY2.setMaxWidth(50);
        attackArcherHBox.getChildren().add(posY2);
        Button attackArcher = new Button();
        attackArcher.setText("Attack archer");
        attackArcher.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!posX2.getText().matches("\\d+")) {
                    InputOutput.output("X should be a number", 'f');
                    return;
                }
                if (!posY2.getText().matches("\\d+")) {
                    InputOutput.output("Y should be a number", 'f');
                    return;
                }
                UnitMenuController.attackArcher(Integer.parseInt(posX2.getText()), Integer.parseInt(posY2.getText()));
            }
        });
        attackArcherHBox.getChildren().add(attackArcher);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setType(String type) {
        this.type = type;
    }
}
