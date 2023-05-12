package Controller;

import Model.Building.*;
import Model.Building.Barracks.Barracks;
import Model.Building.Storage.Storage;
import Model.Building.WeaponMaker.WeaponMaker;
import Model.Game;
import Model.Pair;
import Model.Person.Person;
import Model.Resources.Resource;
import Model.Resources.ResourceType;
import Model.Tile;
import View.Game.GameMenu;
import View.Game.GovernmentMenu;

import java.util.ArrayList;

import static View.InputOutput.output;

public class BuildingMenuController {
    public static void dropBuilding(int x, int y, String type) {
        if (checkSimpleErrorsOfDropBuilding(x, y)) return;
        if (checkNeededTexture(x, y, type)) return;
        if (type.equalsIgnoreCase("killing pit")) {
            GameMenuController.game.getMap().getTiles()[x][y].setHasKillingPit(true);
            output("Killing pit added");
            return;
        }
        if (type.equalsIgnoreCase("pitch ditch")) {
            GameMenuController.game.getMap().getTiles()[x][y].setHasOil(true);
            output("Pitch ditch added");
            return;
        }
        BuildingType buildingType = Building.ALL_BUILDINGS.get(type);
        if (buildingType == null) {
            output("This building does not exists!");
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
        output("building successfully made");
    }

    public static void dropStairs(int x, int y) {
        int mapSize = GameMenuController.mapSize;
        if (x < 0 || y < 0 || x >= mapSize || y >= mapSize) {
            output("Invalid coordinates!");
            return;
        }
        Tile tile = GameMenuController.game.getMap().getTiles()[x][y];
        if (tile.getBuilding() == null || !(tile.getBuilding() instanceof Wall)) {
            output("There is no wall here!");
            return;
        }
        if (!(tile.getBuilding().getOwner().equals(Game.currentGovernment.getUser()))) {
            output("This isn't your wall!");
            return;
        }
        Storage stockpile = (Storage) Game.currentGovernment.findBuildingByName("stockpile");
        if (!stockpile.removeFromStorage(Resource.createResource(ResourceType.STONE, 10))) {
            output("You don't have enough stone");
            return;
        }
        ((Wall) tile.getBuilding()).setHasStairs(true);
        output("Wall successfully created!");
    }

    private static boolean checkNeededTexture(int x, int y, String type) {
        Tile tile = GameMenuController.game.getMap().getTiles()[x][y];
        if (type.equals("wheat farmer") && !tile.getTexture().equals("Grass")) {
            output("Wheat farmer has to be built on top of Grass");
            return true;
        }
        if (type.equals("iron mine") && !tile.getTexture().equals("Iron")) {
            output("Iron mine needs to be built on top of Iron");
            return true;
        }
        if (type.equals("quarry") && !tile.getTexture().equals("Boulder")) {
            output("Quarry needs to be built on top of Boulder");
            return true;
        }
        if (type.equals("pitch rig") && !tile.getTexture().equals("Oil")) {
            output("Pitch rig needs to be built on top of Oil");
            return true;
        }
        if (tile.isHasKillingPit() || tile.isHasOil() || tile.getTexture().equals("Lake") || tile.getTexture().equals("Stone")) {
            output("Can't build on a tile which is Lake or stone or has a killing pit or has oil on it");
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
                output("Not enough resources to buy this building");
                return true;
            }
        }
        if (building.getWorkers() == 0) return false;
        System.out.println("building " + building.getWorkers());
        System.out.println("population " + Game.currentGovernment.getPopulation());
        System.out.println("peasants " + Game.currentGovernment.getPeasants().size());
        if (building.getWorkers() > Game.currentGovernment.getPeasants().size()) {
            output("Not enough workers to create this building!");
            return true;
        }
        return false;
    }

    private static boolean checkSimpleErrorsOfDropBuilding(int x, int y) {
        if (x >= GameMenuController.mapSize || y >= GameMenuController.mapSize) {
            output("Invalid coordinates");
            return true;
        }
        if (GameMenuController.game.getMap().getTiles()[x][y].getBuilding() != null) {
            output("A building already exists on this tile");
            return true;
        }
        if (GameMenuController.game.getMap().getTiles()[x][y].getPeople().size() > 0) {
            output("You cannot build on top of people!");
            return true;
        }
        return false;
    }

    public static void selectBuilding(int x, int y) {
        if (checkSimpleErrorsOfSelectBuilding(x, y)) return;
        Building building = GameMenuController.game.getMap().getTiles()[x][y].getBuilding();
        output("remaining HP: " + building.getHp());
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
            output("Entered Government menu");
            GovernmentMenu governmentMenu = new GovernmentMenu();
            governmentMenu.run();
            output("Exited government menu");
        }
    }

    public static boolean checkSimpleErrorsOfSelectBuilding(int x, int y) {
        if (x >= GameMenuController.mapSize || y >= GameMenuController.mapSize) {
            output("Invalid coordinates");
            return true;
        }
        Building building = GameMenuController.game.getMap().getTiles()[x][y].getBuilding();
        if (building == null) {
            output("No building exists in this tile");
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
            output("This building is not yours");
            return true;
        }
        return false;
    }
}
