package Controller;


import Model.Building.Barracks.Barracks;
import Model.Building.*;
import Model.Building.Storage.Storage;
import Model.Building.WeaponMaker.WeaponMaker;
import Model.Game;
import Model.Person.Person;
import Model.Resources.Resource;
import Model.Resources.ResourceType;
import Model.Tile;
import View.Game.GovernmentMenu;
import View.InputOutput;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.Optional;

public class BuildingMenuController {
    public static void dropBuilding(int x, int y, String type) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setContentText("Are you sure you want to drop a " + type + " in " + x + " " + y);
        confirm.setTitle("Building drop");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isEmpty() || result.get() == ButtonType.CANCEL) {
            return;
        }
        if (checkSimpleErrorsOfDropBuilding(x, y)) return;
        if (checkNeededTexture(x, y, type)) return;
        if (type.equalsIgnoreCase("killing pit")) {
            GameMenuController.game.getMap().getTiles()[x][y].setHasKillingPit(true);
            InputOutput.output("Killing pit added");
            return;
        }
        if (type.equalsIgnoreCase("pitch ditch")) {
            GameMenuController.game.getMap().getTiles()[x][y].setHasOil(true);
            InputOutput.output("Pitch ditch added");
            return;
        }
        BuildingType buildingType = Building.ALL_BUILDINGS.get(type);
        if (buildingType == null) {
            InputOutput.output("This building does not exists!", 'e');
            return;
        }
        Building building = Building.createBuildings(type, x, y, buildingType, Game.currentGovernment.getUser());
        if (building == null) return;
        if (checkIfEnoughResourcesExist(building)) return;
        reduceRecommendedResources(building);
        Game.currentGovernment.getBuildings().add(building);
        GameMenuController.game.getMap().getTiles()[x][y].setBuilding(building);
        if (type.equalsIgnoreCase("inn")) Game.currentGovernment.addInnRate(5);
        if (type.equalsIgnoreCase("church")) Game.currentGovernment.addChurch(10);
        if (type.equalsIgnoreCase("cathedral")) Game.currentGovernment.addChurch(20);
        InputOutput.output("building successfully made");
    }

    public static void dropStairs(int x, int y) {
        int mapSize = GameMenuController.mapSize;
        if (x < 0 || y < 0 || x >= mapSize || y >= mapSize) {
            InputOutput.output("Invalid coordinates!", 'e');
            return;
        }
        Tile tile = GameMenuController.game.getMap().getTiles()[x][y];
        if (tile.getBuilding() == null || !(tile.getBuilding() instanceof Wall)) {
            InputOutput.output("There is no wall here!", 'e');
            return;
        }
        if (!(tile.getBuilding().getOwner().equals(Game.currentGovernment.getUser()))) {
            InputOutput.output("This isn't your wall!", 'e');
            return;
        }
        Storage stockpile = (Storage) Game.currentGovernment.findBuildingByName("stockpile");
        if (!stockpile.removeFromStorage(Resource.createResource(ResourceType.STONE, 10))) {
            InputOutput.output("You don't have enough stone", 'e');
            return;
        }
        ((Wall) tile.getBuilding()).setHasStairs(true);
        InputOutput.output("Wall successfully created!");
    }

    private static boolean checkNeededTexture(int x, int y, String type) {
        Tile tile = GameMenuController.game.getMap().getTiles()[x][y];
        if (type.equals("wheat farmer") && !tile.getTexture().equals("Grass")) {
            InputOutput.output("Wheat farmer has to be built on top of Grass", 'e');
            return true;
        }
        if (type.equals("iron mine") && !tile.getTexture().equals("Iron")) {
            InputOutput.output("Iron mine needs to be built on top of Iron", 'e');
            return true;
        }
        if (type.equals("quarry") && !tile.getTexture().equals("Boulder")) {
            InputOutput.output("Quarry needs to be built on top of Boulder", 'e');
            return true;
        }
        if (type.equals("pitch rig") && !tile.getTexture().equals("Oil")) {
            InputOutput.output("Pitch rig needs to be built on top of Oil", 'e');
            return true;
        }
        if (tile.isHasKillingPit() || tile.isHasOil() || tile.getTexture().equals("Lake") || tile.getTexture().equals("Stone")) {
            InputOutput.output("Can't build on a tile which is Lake or stone or has a killing pit or has oil on it", 'e');
            return true;
        }
        return false;
    }

    private static void reduceRecommendedResources(Building building) {
        for (Resource resource : building.getPrice()) {
            int reduced = 0;
            for (Building checkBuilding : Game.currentGovernment.getBuildings()) {
                if (checkBuilding.getName().equals("stockpile")) {
                    Storage storage = (Storage) checkBuilding;
                    for (Resource stockResource : storage.getStorage()) {
                        if (resource.getResourceType().name.equals(stockResource.getResourceType().name)) {
                            int temp = reduced;
                            reduced += Math.min(resource.getCount() - reduced, stockResource.getCount());
                            stockResource.setCount(stockResource.getCount() -
                                    Math.min(resource.getCount() - temp, stockResource.getCount()));
                        }
                    }
                }
                if (reduced == resource.getCount()) break;
            }
        }
        if (building.getWorkers() == 0) return;
        int i = 0;
        ArrayList<Person> peasants = Game.currentGovernment.getPeasants();
        for (Person person : peasants) {
            i++;
            person.makeWorker();
            if (i == building.getWorkers()) break;
        }
    }

    private static boolean checkIfEnoughResourcesExist(Building building) {
        for (Resource resource : building.getPrice()) {
            int amount = 0;
            for (Building checkBuilding : Game.currentGovernment.getBuildings()) {
                if (checkBuilding.getName().equals("stockpile")) {
                    Storage storage = (Storage) checkBuilding;
                    for (Resource stockResource : storage.getStorage()) {
                        if (resource.getResourceType().name.equals(stockResource.getResourceType().name)) {
                            amount += stockResource.getCount();
                        }
                    }
                }
            }
            if (amount < resource.getCount()) {
                InputOutput.output("Not enough resources to buy this building", 'e');
                return true;
            }
        }
        if (building.getWorkers() == 0) return false;
        System.out.println("building " + building.getWorkers());
        System.out.println("population " + Game.currentGovernment.getPopulation());
        System.out.println("peasants " + Game.currentGovernment.getPeasants().size());
        if (building.getWorkers() > Game.currentGovernment.getPeasants().size()) {
            InputOutput.output("Not enough workers to create this building!", 'e');
            return true;
        }
        return false;
    }

    private static boolean checkSimpleErrorsOfDropBuilding(int x, int y) {
        if (x >= GameMenuController.mapSize || y >= GameMenuController.mapSize) {
            InputOutput.output("Invalid coordinates", 'e');
            return true;
        }
        if (GameMenuController.game.getMap().getTiles()[x][y].getBuilding() != null) {
            InputOutput.output("A building already exists on this tile", 'e');
            return true;
        }
        if (GameMenuController.game.getMap().getTiles()[x][y].getPeople().size() > 0) {
            InputOutput.output("You cannot build on top of people!", 'e');
            return true;
        }
        return false;
    }

    public static void selectBuilding(int x, int y) {
        if (checkSimpleErrorsOfSelectBuilding(x, y)) return;
        Building building = GameMenuController.game.getMap().getTiles()[x][y].getBuilding();
        InputOutput.output("remaining HP: " + building.getHp());
        if (building instanceof WeaponMaker) {
            ((WeaponMaker) building).buyWeapon();
        }
        if (building instanceof Barracks) {
            ((Barracks) building).buyTroop();
        }
        if (building instanceof Church && building.getName().equals("cathedral")) {
            ((Church) building).buyMonk();
        }
        if (building instanceof Keep) {
            InputOutput.output("Entered Government menu");
            GovernmentMenu governmentMenu = new GovernmentMenu();
            governmentMenu.run();
            InputOutput.output("Exited government menu");
        }
    }

    public static boolean checkSimpleErrorsOfSelectBuilding(int x, int y) {
        if (x >= GameMenuController.mapSize || y >= GameMenuController.mapSize) {
            InputOutput.output("Invalid coordinates");
            return true;
        }
        Building building = GameMenuController.game.getMap().getTiles()[x][y].getBuilding();
        if (building == null) {
            InputOutput.output("No building exists in this tile");
            return true;
        }
        boolean flag = false;
        for (Building checkBuilding : Game.currentGovernment.getBuildings()) {
            if (checkBuilding.equals(building)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            InputOutput.output("This building is not yours");
            return true;
        }
        return false;
    }
}
