package Controller;

import Model.Building.Building;
import Model.Building.BuildingType;
import Model.Building.Storage.Storage;
import Model.Building.WeaponMaker.WeaponMaker;
import Model.Game;
import Model.Resources.Resource;

import static View.InputOutput.output;

public class BuildingMenuController {
    public static void dropBuilding(int x, int y, String type) {
        if (checkSimpleErrorsOfDropBuilding(x, y)) return;
        BuildingType buildingType = Building.ALL_BUILDINGS.get(type);
        if (buildingType == null) {
            output("This building does not exists!");
            return;
        }
        Building building = Building.createBuildings(type, x, y, buildingType, Game.currentGovernment.getUser());
        if (checkIfEnoughResourcesExist(building)) return;
        reduceRecommendedResources(building);
        Game.currentGovernment.getBuildings().add(building);
        GameMenuController.game.getMap().getTiles()[x][y].setBuilding(building);
        output("building successfully made");
    }

    private static void reduceRecommendedResources(Building building) {
        for (Resource resource: building.getPrice()) {
            int reduced = 0;
            for (Building checkBuilding: Game.currentGovernment.getBuildings()) {
                if (checkBuilding.getName().equals("stockpile")) {
                    Storage storage = (Storage) checkBuilding;
                    for (Resource stockResource: storage.getStorage()) {
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
    }

    private static boolean checkIfEnoughResourcesExist(Building building) {
        for (Resource resource: building.getPrice()) {
            int amount = 0;
            for (Building checkBuilding: Game.currentGovernment.getBuildings()) {
                if (checkBuilding.getName().equals("stockpile")) {
                    Storage storage = (Storage) checkBuilding;
                    for (Resource stockResource: storage.getStorage()) {
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
        if (building instanceof WeaponMaker) {
            ((WeaponMaker) building).buyWeapon();
        }
    }

    private static boolean checkSimpleErrorsOfSelectBuilding(int x, int y) {
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
        for (Building checkBuilding:Game.currentGovernment.getBuildings()) {
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
