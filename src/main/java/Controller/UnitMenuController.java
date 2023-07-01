package Controller;


import Model.*;
import Model.Building.Building;
import Model.Building.Campfire;
import Model.Building.Storage.Storage;
import Model.Person.Military.MilitaryUnit;
import Model.Person.Military.Special.Engineer;
import Model.Person.Military.Special.Tunneler;
import Model.Person.Person;
import Model.Resources.Resource;
import View.Game.GameMenu;
import View.Game.UnitMenu;



import java.util.ArrayList;

import static View.InputOutput.output;

public class UnitMenuController {
    public static void dropUnit(int x, int y, int count, String type) {
        if (checkSimpleErrorsOfDropUnit(x, y)) return;
        String militaryType = MilitaryUnit.AllMilitaryUnits.get(type);
        if (militaryType == null) {
            output("This type of unit does not exists", 'd');
            return;
        }
        MilitaryUnit militaryUnit = MilitaryUnit.createUnits(type, militaryType, x, y, Game.currentGovernment.getUser());
        if (militaryUnit == null) return;
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
            output("Invalid coordinates", 'd');
            return true;
        }
        if (GameMenuController.game.getMap().getTiles()[x][y].getBuilding() != null) {
            output("You cannot place units on top of buildings!", 'd');
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
                if (checkBuilding.getName().equals("armoury")) {
                    Storage storage = (Storage) checkBuilding;
                    for (Resource stockResource : storage.getStorage()) {
                        if (resource.getResourceType().name.equals(stockResource.getResourceType().name)) {
                            amount += stockResource.getCount();
                        }
                    }
                }
            }
            if (amount < resource.getCount() * count) {
                output("Not enough resources to buy this unit", 'd');
                return true;
            }
        }
        return false;
    }

    public static void showHealth() {
        for (MilitaryUnit militaryUnit : UnitMenu.userUnitInTile) {
            output("1) " + militaryUnit.getName() + ": " + militaryUnit.getDefence() + " hp");
        }
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
                if (checkBuilding.getName().equals("armoury")) {
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

    public static void selectUnit(int x, int y, String type) {
        if (checkSimpleErrorsOfSelectUnit(x, y, type)) return;
        UnitMenu.userUnitInTile = getUserUnitInTile(GameMenuController.game.getMap().getTiles()[x][y].getPeople(), type);
        if (UnitMenu.userUnitInTile.size() == 0) {
            output("There are no units that you can select(They may be on Patrol)");
            return;
        }
        UnitMenu unitMenu = new UnitMenu();
        unitMenu.run();
    }

    private static ArrayList<MilitaryUnit> getUserUnitInTile(ArrayList<Person> game, String type) {
        ArrayList<MilitaryUnit> userUnits = new ArrayList<>();
        for (Person person : game) {
            if (person.getOwner().equals(Game.currentGovernment.getUser()) && person.getName().equals(type) &&
                    !((MilitaryUnit) person).isOnPatrol()) {
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
        for (int i = 0; i < GameMenuController.mapSize; i++) {
            for (int j = 0; j < GameMenuController.mapSize; j++) {
                newTiles[i][j] = new Tile();
                newTiles[i][j].setBuilding(tiles[i][j].getBuilding());
                newTiles[i][j].setTexture(tiles[i][j].getTexture());
                newTiles[i][j].setY(tiles[i][j].getY());
                newTiles[i][j].setX(tiles[i][j].getX());
                newTiles[i][j].setPeople(new ArrayList<>(tiles[i][j].getPeople()));
            }
        }
        for (int i = 0; i < GameMenuController.mapSize; i++) {
            for (int j = 0; j < GameMenuController.mapSize; j++) {
                for (Person person : tiles[i][j].getPeople()) {
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

    public static void checkPatrols() {
        Tile[][] tiles = GameMenuController.game.getMap().getTiles();
        for (int i = 0; i < GameMenuController.mapSize; i++) {
            for (int j = 0; j < GameMenuController.mapSize; j++) {
                for (Person person : tiles[i][j].getPeople()) {
                    if (person instanceof MilitaryUnit && ((MilitaryUnit) person).isOnPatrol()) {
                        MilitaryUnit militaryUnit = (MilitaryUnit) person;
                        if (militaryUnit.getEndPatrol().first == militaryUnit.getX() &&
                                militaryUnit.getEndPatrol().second == militaryUnit.getY()) {
                            militaryUnit.setDestinationY(militaryUnit.getStartPatrol().second);
                            militaryUnit.setDestinationX(militaryUnit.getStartPatrol().first);
                        } else if (militaryUnit.getStartPatrol().first == militaryUnit.getX() &&
                                militaryUnit.getStartPatrol().second == militaryUnit.getY()) {
                            militaryUnit.setDestinationY(militaryUnit.getEndPatrol().second);
                            militaryUnit.setDestinationX(militaryUnit.getEndPatrol().first);
                        }
                    }
                }
            }
        }
    }

    public static void moveUnit(int x, int y) {
        if (x >= GameMenuController.mapSize || y >= GameMenuController.mapSize) {
            output("Invalid coordinates");
            return;
        }
        for (MilitaryUnit militaryUnit : UnitMenu.userUnitInTile) {
            militaryUnit.setDestinationX(x);
            militaryUnit.setDestinationY(y);
        }
    }

    public static void setStatus(String status) {
        for (MilitaryUnit militaryUnit : UnitMenu.userUnitInTile) {
            militaryUnit.setStatus(status);
        }
    }

    public static void attackEnemy(int x, int y) {
        if (x >= GameMenuController.mapSize || y >= GameMenuController.mapSize) {
            output("Invalid coordinates");
            return;
        }
        moveUnit(x, y);
        output("Your troop are moving towards the enemy");
    }

    public static void digTunnel(int x, int y) {
        if (!(UnitMenu.userUnitInTile.get(0) instanceof Tunneler)) {
            output("Your selected units are not tunnelers!");
            return;
        }
        for (Person person : UnitMenu.userUnitInTile) {
            ((Tunneler) person).setUnderTunnel(true);
        }
        moveUnit(x, y);

    }

    public static void attackArcher(int x, int y) {
        if (x >= GameMenuController.mapSize || y >= GameMenuController.mapSize) {
            output("Invalid coordinates");
            return;
        }
        ArrayList<Person> peopleInTile = GameMenuController.game.getMap().getTiles()[x][y].getPeople();
        Building building = GameMenuController.game.getMap().getTiles()[x][y].getBuilding();
        for (MilitaryUnit militaryUnit : UnitMenu.userUnitInTile) {
            if (militaryUnit.getRange() > 0 && checkInRange(militaryUnit, x, y)) {
                for (Person person : peopleInTile) {
                    if (person instanceof MilitaryUnit && !person.getOwner().equals(militaryUnit.getOwner())) {
                        ((MilitaryUnit) person).reduceDefence(militaryUnit.getAttack());
                    }
                }
                if (building != null && !building.getOwner().equals(militaryUnit.getOwner())) {
                    building.reduceHP(militaryUnit.getAttack());
                }
                if (GameMenuController.game.getMap().getTiles()[x][y].isHasOil()) {
                    GameMenuController.game.getMap().getTiles()[x][y].setHasOil(false);
                    GameMenuController.game.getMap().getTiles()[x][y].setOnFire(true);
                }
            }
        }
        output("Archers who have the possible range attacked the enemies and buildings");
    }

    private static boolean checkInRange(MilitaryUnit militaryUnit, int x, int y) {
        int distance = (militaryUnit.getX() - x) * (militaryUnit.getX() - x) + (militaryUnit.getY() - y) * (militaryUnit.getY() - y);
        return distance <= militaryUnit.getRange() * militaryUnit.getRange();
    }

    public static void disbandUnit() {
        Campfire campfire = (Campfire) Game.currentGovernment.findBuildingByName("campfire");
        int x = campfire.getX();
        int y = campfire.getY();
        moveUnit(x, y);
        setStatus("standing");
        for (MilitaryUnit militaryUnit : UnitMenu.userUnitInTile) {
            militaryUnit.setOnPatrol(false);
        }
    }

    public static boolean patrolUnit(int x1, int x2, int y1, int y2) {
        if (x1 >= GameMenuController.mapSize || y1 >= GameMenuController.mapSize) {
            output("Invalid coordinates");
            return false;
        }
        if (x2 >= GameMenuController.mapSize || y2 >= GameMenuController.mapSize) {
            output("Invalid coordinates");
            return false;
        }
        for (MilitaryUnit militaryUnit : UnitMenu.userUnitInTile) {
            militaryUnit.setOnPatrol(true);
            militaryUnit.setStartPatrol(new Pair(x1, y1));
            militaryUnit.setEndPatrol(new Pair(x2, y2));
        }
        moveUnit(x2, y2);
        output("units in this unit started Patrolling");
        return true;
    }

    public static void cancelPatrol(int x, int y) {
        if (x >= GameMenuController.mapSize || y >= GameMenuController.mapSize) {
            output("Invalid coordinates");
            return;
        }
        for (Person person : GameMenuController.game.getMap().getTiles()[x][y].getPeople()) {
            if (person instanceof MilitaryUnit) {
                MilitaryUnit militaryUnit = (MilitaryUnit) person;
                if (militaryUnit.isOnPatrol()) {
                    militaryUnit.setOnPatrol(false);
                    militaryUnit.setDestinationX(militaryUnit.getX());
                    militaryUnit.setDestinationY(militaryUnit.getY());
                }
            }
        }
        output("Canceled Patrol for all units in this tile");
    }

    public static void pourOil(char direction) {
        if (direction != 'n' && direction != 's' && direction != 'w' && direction != 'e') {
            output("Invalid direction!");
            return;
        }
        if (!(UnitMenu.userUnitInTile.get(0) instanceof Engineer)) {
            output("Your selected units are not engineers!");
            return;
        }
        for (MilitaryUnit militaryUnit : UnitMenu.userUnitInTile) {
            ((Engineer) militaryUnit).pourOil(direction);
        }
    }

    public static void buildEquipment() {
        if (!(UnitMenu.userUnitInTile.get(0) instanceof Engineer)) {
            output("Your selected units are not engineers!");
        }
        int x = UnitMenu.userUnitInTile.get(0).getX();
        int y = UnitMenu.userUnitInTile.get(0).getY();
//        for (MilitaryUnit militaryUnit:UnitMenu.userUnitInTile) {
//            ((Engineer) militaryUnit).createSiege();
//        }
        int lastSize = -1;
        while (UnitMenu.userUnitInTile.size() != 0) {
            UnitMenu.userUnitInTile = getUserUnitInTile(GameMenuController.game.getMap().getTiles()[x][y].getPeople(), "engineer");
            if (lastSize == UnitMenu.userUnitInTile.size()) break;
            if (UnitMenu.userUnitInTile.size() == 0) break;
            lastSize = UnitMenu.userUnitInTile.size();
            ((Engineer) UnitMenu.userUnitInTile.get(0)).createSiege();
        }
    }

    public static void attackWithStatus() {
        for (Government government : GameMenuController.game.getGovernments()) {
            for (Person person : government.getPeople()) {
                if (!(person instanceof MilitaryUnit)) continue;
                MilitaryUnit militaryUnit = (MilitaryUnit) person;
                if (militaryUnit.getDestinationY() != militaryUnit.getY() ||
                        militaryUnit.getDestinationX() != militaryUnit.getX()) continue;
                int range = -1;
                switch (militaryUnit.getStatus()) {
                    case "standing":
                        range = 0;
                        break;
                    case "defensive":
                        range = 5;
                        break;
                    case "offensive":
                        range = 10;
                        break;
                }
                MilitaryUnit enemy = militaryUnit.scan(range);
                if (enemy == null) return;
                PathFinder pathFinder = new PathFinder(militaryUnit.getAbleToPass(), GameMenuController.mapSize,
                        militaryUnit.getX(), enemy.getX(), militaryUnit.getY(), enemy.getY());
                pathFinder.shortestPath();
                ArrayList<Pair> path = pathFinder.getPath();
                if (path == null) {
                    militaryUnit.setDestinationX(militaryUnit.getX());
                    militaryUnit.setDestinationY(militaryUnit.getY());
                    return;
                }
                path = militaryUnit.reverseArrayList(path);
                militaryUnit.setDestinationX(path.get(Math.min(path.size() - 1, (militaryUnit.getSpeed() + 2) / 3)).first);
                militaryUnit.setDestinationY(path.get(Math.min(path.size() - 1, (militaryUnit.getSpeed() + 2) / 3)).second);
            }
        }
    }

    public static void digMoat(int x, int y) {
        for (MilitaryUnit militaryUnit : UnitMenu.userUnitInTile) {
            militaryUnit.digMoat(x, y);
        }
    }

    public static void fillMoat(int x, int y) {
        for (MilitaryUnit militaryUnit : UnitMenu.userUnitInTile) {
            militaryUnit.fillMoat(x, y);
        }
    }
}
