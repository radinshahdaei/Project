package Controller;

import Model.Building.Building;
import Model.Building.BuildingType;
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
        Building building = Building.createBuildings(type, x, y, buildingType);
        if (checkIfEnoughResourcesExist(building)) return;
        reduceRecommendedResources(building);
        Game.currentGovernment.getBuildings().add(building);
        GameMenuController.game.getMap().getTiles()[x][y].setBuilding(building);
        output("building successfully made");
    }

    private static void reduceRecommendedResources(Building building) {
        for (Resource resource: building.getPrice()) {
            for (Resource governmentResource:Game.currentGovernment.getResources()) {
                if (resource.getResourceType().name.equals(governmentResource.getResourceType().name)) {
                    governmentResource.setCount(governmentResource.getCount() - resource.getCount());
                }
            }
        }
    }

    private static boolean checkIfEnoughResourcesExist(Building building) {
        for (Resource resource: building.getPrice()) {
            boolean flag = false;
            for (Resource governmentResource:Game.currentGovernment.getResources()) {
                if (resource.getResourceType().name.equals(governmentResource.getResourceType().name)) {
                    flag = true;
                    if (resource.getCount() > governmentResource.getCount()) {
                        output("Not enough resources to buy this building");
                        return true;
                    }
                }
            }
            if (!flag) {
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
        output("Selection successful");
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
