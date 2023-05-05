package Model.Person.Military;

import Controller.GameMenuController;
import Model.*;
import Model.Person.Military.Siege.Siege;
import Model.Person.Military.Soldier.Soldier;
import Model.Person.Military.Special.Engineer;
import Model.Person.Military.Special.Ladderman;
import Model.Person.Military.Special.Tunneler;
import Model.Person.Person;
import Model.Person.PersonType;
import Model.Resources.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MilitaryUnit extends Person {
    public static HashMap<String , String> AllMilitaryUnits = new HashMap<>();
    static {
        AllMilitaryUnits.put("archer", "soldier");
        AllMilitaryUnits.put("crossbowman","soldier");
        AllMilitaryUnits.put("spearman","soldier");
        AllMilitaryUnits.put("pikeman","soldier");
        AllMilitaryUnits.put("maceman","soldier");
        AllMilitaryUnits.put("swordsman","soldier");
        AllMilitaryUnits.put("knight","soldier");
        AllMilitaryUnits.put("monk","soldier");
        AllMilitaryUnits.put("arabian archer","soldier");
        AllMilitaryUnits.put("slave","soldier");
        AllMilitaryUnits.put("slinger","soldier");
        AllMilitaryUnits.put("horse archer","soldier");
        AllMilitaryUnits.put("arabian swordsman","soldier");
        AllMilitaryUnits.put("fire thrower","soldier");
        AllMilitaryUnits.put("assassin","soldier");

        AllMilitaryUnits.put("catapult","siege");
        AllMilitaryUnits.put("trebuchet","siege");
        AllMilitaryUnits.put("siege tower","siege");
        AllMilitaryUnits.put("portable shield","siege");
        AllMilitaryUnits.put("arabic fire ballista","siege");
        AllMilitaryUnits.put("battering ram","siege");

        AllMilitaryUnits.put("engineer","engineer");
        AllMilitaryUnits.put("ladderman","ladderman");
        AllMilitaryUnits.put("tunneler","tunneler");
    }
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
    boolean[][] ableToPass = new boolean[GameMenuController.mapSize][GameMenuController.mapSize];

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
        if (type.equals("siege")) {
            militaryUnit = Siege.createUnit(name, x, y, owner);
        }
        else if (type.equals("soldier")) {
            militaryUnit = Soldier.createUnit(name, x, y, owner);
        }
        else if (type.equals("engineer")) {
            militaryUnit = Engineer.createUnit(x, y, owner);
        }
        else if (type.equals("ladderman")) {
            militaryUnit = Ladderman.createUnit(x, y, owner);
        }
        else if (type.equals("tunneler")) {
            militaryUnit = Tunneler.createUnit(x, y, owner);
        }
        return militaryUnit;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
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

    public int getDefence() {
        return defence;
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

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public void reduceDefence(int amount) {
        this.defence -= amount;
    }

    public Pair move() {
        this.fixAbleToPass();
        PathFinder pathFinder = new PathFinder(this.ableToPass, GameMenuController.mapSize, x, destinationX, y, destinationY);
        pathFinder.shortestPath();
        ArrayList<Pair> path = pathFinder.getPath();
        path = reverseArrayList(path);
        return path.get(Math.min(path.size() - 1, (this.speed + 2) / 3));
    }

    public void fixAbleToPass() {
        Map map = GameMenuController.game.getMap();
        for (int i = 0 ; i < GameMenuController.mapSize ; i++) {
            for (int j = 0 ; j < GameMenuController.mapSize ; j++) {
                this.ableToPass[i][j] = checkPassTile(map.getTiles()[i][j]);
            }
        }
    }

    private boolean checkPassTile(Tile tile) {
        if (tile.getBuilding() != null && tile.getBuilding().getName().equals("campfire")) {
            return false;
        }
        if (tile.getTexture().equals("Stone") || tile.getTexture().equals("Lake")) {
            return false;
        }
        return true;
    }

    private ArrayList<Pair> reverseArrayList(ArrayList<Pair> path) {
        ArrayList<Pair> newPath = new ArrayList<>();
        for (int i = path.size() - 1 ; i > -1 ; i--) {
            newPath.add(path.get(i));
        }
        return newPath;
    }

}
