package View.Game;

import Model.Map;
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

        Button endGame = new Button();
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