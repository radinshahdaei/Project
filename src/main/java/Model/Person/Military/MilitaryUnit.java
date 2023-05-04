package Model.Person.Military;

import Model.Government;
import Model.Person.Person;
import Model.Person.PersonType;
import Model.Resources.Resource;

import java.util.ArrayList;

public class MilitaryUnit extends Person {
    private int x;
    private int y;
    private int speed;
    private int attack;
    private int defence;
    private int range;
    private Government military;
    private ArrayList<Resource> price;


    public MilitaryUnit(String name, int x, int y, int speed, int attack, int defence, int range, Government military, ArrayList<Resource> price) {
        super(name, PersonType.MILITARY_UNIT);
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.attack = attack;
        this.defence = defence;
        this.range = range;
        this.military = military;
        this.price = price;
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

    public Government getMilitary() {
        return military;
    }

    public ArrayList<Resource> getPrice() {
        return price;
    }
}
