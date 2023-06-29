package View.Game;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

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

        Button goToGovernmentMenu = new Button();
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
