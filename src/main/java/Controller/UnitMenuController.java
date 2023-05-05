package Controller;


import Model.Building.Building;
import Model.Building.Storage.Storage;
import Model.Game;
import Model.Person.Military.MilitaryUnit;
import Model.Resources.Resource;

import static View.InputOutput.output;

public class UnitMenuController {
    public static void dropUnit(int x, int y, int count, String type) {
        if (checkSimpleErrorsOfDropUnit(x, y)) return;
        String militaryType = MilitaryUnit.AllMilitaryUnits.get(type);
        if (militaryType == null) {
            output("This type of unit does not exists");
            return;
        }
        MilitaryUnit militaryUnit = MilitaryUnit.createUnits(type, militaryType, x, y, Game.currentGovernment.getUser());
        if (checkIfEnoughResourcesExist(militaryUnit, count)) return;
        reduceRecommendedResources(militaryUnit, count);
        for (int i = 0; i < count; i++) {
            militaryUnit = MilitaryUnit.createUnits(type, militaryType, x, y, Game.currentGovernment.getUser());
            Game.currentGovernment.getPeople().add(militaryUnit);
            GameMenuController.game.getMap().getTiles()[x][y].getPeople().add(militaryUnit);
        }
        output("units successfully dropped");
    }

    private static boolean checkSimpleErrorsOfDropUnit(int x, int y) {
        if (x >= GameMenuController.mapSize || y >= GameMenuController.mapSize) {
            output("Invalid coordinates");
            return true;
        }
        if (GameMenuController.game.getMap().getTiles()[x][y].getBuilding() != null) {
            output("You cannot place units on top of buildings!");
            return true;
        }
        return false;
    }

    private static boolean checkIfEnoughResourcesExist(MilitaryUnit militaryUnit, int count) {
        for (Resource resource : militaryUnit.getPrice()) {
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
            if (amount < resource.getCount() * count) {
                output("Not enough resources to buy this building");
                return true;
            }
        }
        return false;
    }

    private static void reduceRecommendedResources(MilitaryUnit militaryUnit, int count) {
        for (Resource resource : militaryUnit.getPrice()) {
            int reduced = 0;
            for (Building checkBuilding : Game.currentGovernment.getBuildings()) {
                if (checkBuilding.getName().equals("stockpile")) {
                    Storage storage = (Storage) checkBuilding;
                    for (Resource stockResource : storage.getStorage()) {
                        if (resource.getResourceType().name.equals(stockResource.getResourceType().name)) {
                            int temp = reduced;
                            reduced += Math.min(resource.getCount() * count - reduced, stockResource.getCount());
                            stockResource.setCount(stockResource.getCount() -
                                    Math.min(resource.getCount() * count - temp, stockResource.getCount()));
                        }
                    }
                }
                if (reduced == resource.getCount() * count) break;
            }
        }
    }
}
