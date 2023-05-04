package Model.Person.Military;

import Model.Government;
import Model.Person.Military.Siege.Siege;
import Model.Person.Military.Soldier.Soldier;
import Model.Person.Military.Special.Engineer;
import Model.Person.Military.Special.Ladderman;
import Model.Person.Military.Special.Tunneler;
import Model.Person.Person;
import Model.Person.PersonType;
import Model.Resources.Resource;
import Model.User;

import java.util.ArrayList;
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
    private int speed;
    private int attack;
    private int defence;
    private int range;
    private ArrayList<Resource> price;


    public MilitaryUnit(String name, int x, int y, int speed, int attack, int defence, int range, ArrayList<Resource> price, User owner) {
        super(name, PersonType.MILITARY_UNIT, owner);
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.attack = attack;
        this.defence = defence;
        this.range = range;
        this.price = price;
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

    public ArrayList<Resource> getPrice() {
        return price;
    }
}
