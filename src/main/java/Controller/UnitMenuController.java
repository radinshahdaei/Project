package Controller;


import Model.Building.Building;
import Model.Building.Storage.Storage;
import Model.Game;
import Model.Map;
import Model.Pair;
import Model.Person.Military.MilitaryUnit;
import Model.Person.Person;
import Model.Resources.Resource;
import Model.Tile;
import View.Game.UnitMenu;


import java.util.ArrayList;

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
        for (int i = 0 ; i < count ; i++) {
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
        for (Resource resource: militaryUnit.getPrice()) {
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
            if (amount < resource.getCount() * count) {
                output("Not enough resources to buy this building");
                return true;
            }
        }
        return false;
    }

    private static void reduceRecommendedResources(MilitaryUnit militaryUnit, int count) {
        for (Resource resource: militaryUnit.getPrice()) {
            int reduced = 0;
            for (Building checkBuilding: Game.currentGovernment.getBuildings()) {
                if (checkBuilding.getName().equals("stockpile")) {
                    Storage storage = (Storage) checkBuilding;
                    for (Resource stockResource: storage.getStorage()) {
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

    public static void selectUnit(int x, int y, String type) {
        if (checkSimpleErrorsOfSelectUnit(x, y, type)) return;
        UnitMenu.userUnitInTile = getUserUnitInTile(GameMenuController.game.getMap().getTiles()[x][y].getPeople(), type);
        UnitMenu unitMenu = new UnitMenu();
        unitMenu.run();
    }

    private static ArrayList<MilitaryUnit> getUserUnitInTile(ArrayList<Person> game, String type) {
        ArrayList<MilitaryUnit> userUnits = new ArrayList<>();
        for (Person person : game) {
            if (person.getOwner().equals(Game.currentGovernment.getUser()) && person.getName().equals(type)) {
                userUnits.add((MilitaryUnit) person);
            }
        }
        return userUnits;
    }

    private static boolean checkSimpleErrorsOfSelectUnit(int x, int y, String type) {
        if (x >= GameMenuController.mapSize || y >= GameMenuController.mapSize) {
            output("Invalid coordinates");
            return true;
        }
        ArrayList<MilitaryUnit> units = getUserUnitInTile(GameMenuController.game.getMap().getTiles()[x][y].getPeople(), type);
        if (units.size() == 0) {
            output("You do not have any units of this type in this tile");
            return true;
        }
        return false;
    }

    public static void moveAllMilitaryUnits() {
        Tile[][] tiles = GameMenuController.game.getMap().getTiles();
        Tile[][] newTiles = new Tile[GameMenuController.mapSize][GameMenuController.mapSize];
        for (int i = 0 ; i < GameMenuController.mapSize ; i++) {
            for (int j = 0 ; j < GameMenuController.mapSize ; j++) {
                newTiles[i][j] = new Tile();
                newTiles[i][j].setBuilding(tiles[i][j].getBuilding());
                newTiles[i][j].setTexture(tiles[i][j].getTexture());
                newTiles[i][j].setY(tiles[i][j].getY());
                newTiles[i][j].setX(tiles[i][j].getX());
                newTiles[i][j].setPeople(new ArrayList<>(tiles[i][j].getPeople()));
            }
        }
        for (int i = 0 ; i < GameMenuController.mapSize ; i++) {
            for (int j = 0 ; j < GameMenuController.mapSize ; j++) {
                for (Person person:tiles[i][j].getPeople()) {
                    if (person instanceof MilitaryUnit) {
                        MilitaryUnit militaryUnit = (MilitaryUnit) person;
                        Pair pair = militaryUnit.move();
                        newTiles[i][j].getPeople().remove(person);
                        newTiles[pair.first][pair.second].getPeople().add(person);
                        militaryUnit.setY(pair.second);
                        militaryUnit.setX(pair.first);
                    }
                }
            }
        }
        GameMenuController.game.getMap().setTiles(newTiles);
    }

    public static void moveUnit(int x, int y) {
        for (MilitaryUnit militaryUnit:UnitMenu.userUnitInTile) {
            militaryUnit.setDestinationX(x);
            militaryUnit.setDestinationY(y);
        }
    }
}
