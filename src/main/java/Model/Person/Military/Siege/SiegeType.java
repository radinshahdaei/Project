package Model.Person.Military.Siege;

import Model.Resources.Resource;

import java.util.ArrayList;

import static Model.Resources.Resource.getResources;

public enum SiegeType {
    CATAPULT("catapult", 5, 50, 150, 15, 2, getResources("gold", "150")),
    TREBUCHET("trebuchet", 0, 50, 150, 15, 3, getResources("gold", "50")),
    SIEGE_TOWER("siege tower", 5, 0, 150, 0, 4, getResources("gold", "150")),
    PORTABLE_SHIELD("portable shield", 8, 0, 100, 0, 1, getResources("gold", "5")),
    ARABIC_FIRE_BALLISTA("arabic fire ballista", 5, 50, 150, 15, 2, getResources("gold", "150")), //set fire
    BATTERING_RAMS("battering ram", 8, 100, 150, 0, 4, getResources("gold", "200"));


    private String name;
    private int speed;
    private int attack;
    private int defence;
    private int range;
    private int engineersNeeded;
    private ArrayList<Resource> price;

    SiegeType(String name, int speed, int attack, int defence, int range, int engineersNeeded, ArrayList<Resource> price) {
        this.name = name;
        this.speed = speed;
        this.attack = attack;
        this.defence = defence;
        this.range = range;
        this.engineersNeeded = engineersNeeded;
        this.price = price;
    }
}
