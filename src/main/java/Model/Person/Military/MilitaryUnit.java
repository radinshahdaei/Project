package Model.Person.Military;

import Controller.GameMenuController;
import Model.*;
import Model.Building.Building;
import Model.Building.DeffensiveBuilding.DefensiveBuilding;
import Model.Building.Moat;
import Model.Building.Wall;
import Model.Person.Military.Siege.Siege;
import Model.Person.Military.Soldier.Soldier;
import Model.Person.Military.Special.Engineer;
import Model.Person.Military.Special.Ladderman;
import Model.Person.Military.Special.Tunneler;
import Model.Person.Person;
import Model.Person.PersonType;
import Model.Resources.Resource;

import java.util.ArrayList;
import java.util.HashMap;

import static Controller.BuildingMenuController.checkSimpleErrorsOfSelectBuilding;
import static View.InputOutput.output;

public class MilitaryUnit extends Person {
    public static HashMap<String, String> AllMilitaryUnits = new HashMap<>();

    static {
        AllMilitaryUnits.put("archer", "soldier");
        AllMilitaryUnits.put("crossbowman", "soldier");
        AllMilitaryUnits.put("spearman", "soldier");
        AllMilitaryUnits.put("pikeman", "soldier");
        AllMilitaryUnits.put("maceman", "soldier");
        AllMilitaryUnits.put("swordsman", "soldier");
        AllMilitaryUnits.put("knight", "soldier");
        AllMilitaryUnits.put("monk", "soldier");
        AllMilitaryUnits.put("arabian archer", "soldier");
        AllMilitaryUnits.put("slave", "soldier");
        AllMilitaryUnits.put("slinger", "soldier");
        AllMilitaryUnits.put("horse archer", "soldier");
        AllMilitaryUnits.put("arabian swordsman", "soldier");
        AllMilitaryUnits.put("fire thrower", "soldier");
        AllMilitaryUnits.put("assassin", "soldier");

        AllMilitaryUnits.put("catapult", "siege");
        AllMilitaryUnits.put("trebuchet", "siege");
        AllMilitaryUnits.put("siege tower", "siege");
        AllMilitaryUnits.put("portable shield", "siege");
        AllMilitaryUnits.put("arabic fire ballista", "siege");
        AllMilitaryUnits.put("battering ram", "siege");

        AllMilitaryUnits.put("engineer", "engineer");
        AllMilitaryUnits.put("ladderman", "ladderman");
        AllMilitaryUnits.put("tunneler", "tunneler");
    }

    boolean[][] ableToPass = new boolean[GameMenuController.mapSize][GameMenuController.mapSize];
    private int x;
    private int y;
    private int destinationX;
    private int destinationY;
    private int speed;
    private int attack;
    private int defence;
    private int range;
    private ArrayList<Resource> price;
    private String status;
    private boolean onPatrol;
    private Pair startPatrol;
    private Pair endPatrol;

    public MilitaryUnit(String name, int x, int y, int speed, int attack, int defence, int range, ArrayList<Resource> price, User owner) {
        super(name, PersonType.MILITARY_UNIT, owner);
        this.x = x;
        this.y = y;
        this.destinationX = x;
        this.destinationY = y;
        this.speed = speed;
        this.attack = attack;
        this.defence = defence;
        this.range = range;
        this.price = price;
        this.status = "standing";
        this.onPatrol = false;
    }

    public static MilitaryUnit createUnits(String name, String type, int x, int y, User owner) {
        MilitaryUnit militaryUnit = null;
        if (type.equals("soldier")) {
            militaryUnit = Soldier.createUnit(name, x, y, owner);
        } else if (type.equals("engineer")) {
            militaryUnit = Engineer.createUnit(x, y, owner);
        } else if (type.equals("ladderman")) {
            militaryUnit = Ladderman.createUnit(x, y, owner);
            militaryUnit.makeMilitary();
        } else if (type.equals("tunneler")) {
            militaryUnit = Tunneler.createUnit(x, y, owner);
        }
        return militaryUnit;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDestinationX() {
        return destinationX;
    }

    public void setDestinationX(int destinationX) {
        this.destinationX = destinationX;
    }

    public int getDestinationY() {
        return destinationY;
    }

    public void setDestinationY(int destinationY) {
        this.destinationY = destinationY;
    }

    public int getSpeed() {
        return speed;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getRange() {
        return range;
    }

    public boolean isOnPatrol() {
        return onPatrol;
    }

    public void setOnPatrol(boolean onPatrol) {
        this.onPatrol = onPatrol;
    }

    public Pair getStartPatrol() {
        return startPatrol;
    }

    public void setStartPatrol(Pair startPatrol) {
        this.startPatrol = startPatrol;
    }

    public Pair getEndPatrol() {
        return endPatrol;
    }

    public void setEndPatrol(Pair endPatrol) {
        this.endPatrol = endPatrol;
    }

    public ArrayList<Resource> getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public void reduceDefence(int amount) {
        this.defence -= amount;
        if (this.defence < 0) this.defence = 0;
    }

    public boolean[][] getAbleToPass() {
        return ableToPass;
    }

    public Pair move() {
        this.fixAbleToPass();
        PathFinder pathFinder = new PathFinder(this.ableToPass, GameMenuController.mapSize, x, destinationX, y, destinationY);
        pathFinder.shortestPath();
        ArrayList<Pair> path = pathFinder.getPath();
        if (path == null) {
            this.destinationY = this.y;
            this.destinationX = this.x;
            return new Pair(this.x, this.y);
        }
        path = reverseArrayList(path);
        return path.get(Math.min(path.size() - 1, (this.speed + 2) / 3));
    }

    public int getDistance(int x1, int y1, int x2, int y2) {
        int d1 = (int) Math.pow(x1 - x2, 2);
        int d2 = (int) Math.pow(y1 - y2, 2);
        return (int) Math.sqrt(d1 + d2);
    }

    public MilitaryUnit getLowestHealth(Tile tile, User owner) {
        ArrayList<Person> people = tile.getPeople(owner);
        Person personWithLowestHealth = null;
        int lowestHealth = 1000000;
        if (people.size() == 0) return null;
        for (Person person : people) {
            if (((MilitaryUnit) person).getDefence() < lowestHealth) {
                personWithLowestHealth = person;
                lowestHealth = ((MilitaryUnit) person).getDefence();
            }
        }
        return ((MilitaryUnit) personWithLowestHealth);
    }

    public MilitaryUnit scan(int range) {
        User owner = super.getOwner();
        MilitaryUnit scanned = null;
        int mapSize = GameMenuController.mapSize;
        int minDistance = 1000000;
        int distance;
        Tile[][] tiles = GameMenuController.game.getMap().getTiles();
        int startingX = Math.max(x - range, 0);
        int startingY = Math.max(y - range, 0);
        int endingX = Math.min(x + range, mapSize - 1);
        int endingY = Math.min(y + range, mapSize - 1);
        for (int i = startingX; i <= endingX; i++) {
            for (int j = startingY; j <= endingY; j++) {
                if (tiles[i][j].getPeople(owner).size() == 0) continue;
                distance = getDistance(x, y, i, j);
                if (distance < minDistance) {
                    minDistance = distance;
                    scanned = getLowestHealth(tiles[i][j], owner);
                }
            }
        }
        return scanned;
    }

    public void digMoat(int x, int y) { //TODO run when dig moat is typed
        if (checkSimpleErrorsOfSelectBuilding(x, y)) {
            output("You can't dig a moat here!");
            return;
        }
        Building moat = new Moat(x, y, getOwner());
        GameMenuController.game.getMap().getTiles()[x][y].setBuilding(moat);
    }

    public void fillMoat(int x, int y) { //TODO run when fill moat is added
        if (this instanceof Siege) {
            output("Siege machine can't fill a moat dumbass!");
            return;
        }
        Building building = GameMenuController.game.getMap().getTiles()[x][y].getBuilding();
        if (building == null) return;
        if (!(building instanceof Moat)) return;
        if (!(building.getOwner().equals(getOwner()))) return;
        GameMenuController.game.getMap().getTiles()[x][y].setBuilding(null);
        output("Moat filled up!");
    }


    public void batteringRamAttack() {
        if (this instanceof Siege) {
            output("Siege machine can't dig a moat dumbass!");
            return;
        }
        Tile[][] tiles = GameMenuController.game.getMap().getTiles();
        Building building = tiles[this.getX()][this.getY()].getBuilding();
        if (!this.getName().equals("battering ram")) return;
        if (!(building instanceof Wall)) return;
        building.reduceHP(this.attack);
    }


    public void attack() {
        int addedRange = 0;
        int addedDamage = 0;
        Tile[][] tiles = GameMenuController.game.getMap().getTiles();
        Building building = tiles[x][y].getBuilding();
        if (building instanceof DefensiveBuilding) {
            addedRange = ((DefensiveBuilding) building).getFireRange();
            addedDamage = ((DefensiveBuilding) building).getDamageAdded();
        }
        MilitaryUnit scanned = scan(this.range + addedRange);
        if (scanned == null) return;
        scanned.reduceDefence(this.attack + addedDamage);
        batteringRamAttack();
    }

    public void fixAbleToPass() {
        Map map = GameMenuController.game.getMap();
        for (int i = 0; i < GameMenuController.mapSize; i++) {
            for (int j = 0; j < GameMenuController.mapSize; j++) {
                this.ableToPass[i][j] = checkPassTile(map.getTiles()[i][j]);
                if (i == this.destinationX && j == this.destinationY) {
                    this.ableToPass[i][j] = checkPassEndTile(map.getTiles()[i][j]);
                }
            }
        }
    }

    private boolean checkPassTile(Tile tile) {
        if (tile.getTexture().equals("Stone") || tile.getTexture().equals("Lake")) {
            return false;
        }
//        if (tile.isOnFire() || tile.isHasKillingPit()) return false;
        Building building;
        if (this instanceof Soldier) {
            Soldier soldier = (Soldier) this;
            if (this.getName().equals("assassin")) return true;
            if ((building = tile.getBuilding()) != null) {
                if (building instanceof Wall) {
                    Wall wall = (Wall) building;
                    if (wall.isHasLadder() && soldier.getCanUseLadder()) return true;
                    if ((wall.isHasStairs() || wall.isGateHouse()) && soldier.getOwner().equals(wall.getOwner()))
                        return true;
                    if (wall.isHasSiegeTower() && soldier.getOwner().equals(wall.getSiegeTowerOwner())) return true;
                    if (wall.isGateHouse() && wall.isCaptured()) return true;
                    if (wall.getHp() <= 0) return true;
                    return false;
                }
                if (building.getHp() <= 0) return true;
                return false;
            }
            return true;
        } else {
            if ((building = tile.getBuilding()) != null) {
                if (building.getHp() <= 0) return true;
                return false;
            }
            return true;
        }
    }

    private boolean checkPassEndTile(Tile tile) {
        if (this instanceof Ladderman || this.getName().equals("siege tower") || this.getName().equals("battering ram")) {
            if (tile.getTexture().equals("Stone") || tile.getTexture().equals("Lake")) {
                return false;
            }
//            if (tile.isOnFire() || tile.isHasKillingPit()) return false;
            Building building;
            if ((building = tile.getBuilding()) != null) {
                if (building.getHp() <= 0) return true;
                if (building instanceof Wall) return true;
                return false;
            }
            return true;
        }
        if (this instanceof Engineer || this.range > 0) {
            if (tile.getTexture().equals("Stone") || tile.getTexture().equals("Lake")) {
                return false;
            }
//            if (tile.isOnFire() || tile.isHasKillingPit()) return false;
            Building building;
            if (this instanceof Soldier) {
                Soldier soldier = (Soldier) this;
                if (this.getName().equals("assassin")) return true;
                if ((building = tile.getBuilding()) != null) {
                    if (building instanceof Wall) {
                        Wall wall = (Wall) building;
                        if (wall.isHasLadder() && soldier.getCanUseLadder()) return true;
                        if ((wall.isHasStairs() || wall.isGateHouse()) && soldier.getOwner().equals(wall.getOwner()))
                            return true;
                        if (wall.isHasSiegeTower() && soldier.getOwner().equals(wall.getSiegeTowerOwner())) return true;
                        if (wall.isGateHouse() && wall.isCaptured()) return true;
                        if (wall.getHp() <= 0) return true;
                        return false;
                    }
                    if (building.getHp() <= 0) return true;
                    if (building instanceof DefensiveBuilding && building.getOwner().equals(soldier.getOwner()))
                        return true;
                    return false;
                }
                return true;
            } else {
                if ((building = tile.getBuilding()) != null) {
                    if (building.getHp() <= 0) return true;
                    if (building instanceof DefensiveBuilding && building.getOwner().equals(this.getOwner()))
                        return true;
                    return false;
                }
                return true;
            }

        }
        return checkPassTile(tile);
    }

    public ArrayList<Pair> reverseArrayList(ArrayList<Pair> path) {
        ArrayList<Pair> newPath = new ArrayList<>();
        for (int i = path.size() - 1; i > -1; i--) {
            newPath.add(path.get(i));
        }
        return newPath;
    }
}
