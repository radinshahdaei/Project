package View.Game;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

public class MenusGUI {
    private Pane menuPane;

    public MenusGUI(Pane menuPane) {
        this.menuPane = menuPane;
    }

    public void createGUI() {
        Button goToBuildingMenu = new Button();
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
