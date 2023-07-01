package Server.Model.Person.Military.Siege;

import Client.Model.Person.Military.Siege.SiegeType;
import Client.Model.Person.Military.Special.Engineer;
import Client.Model.Person.Military.MilitaryUnit;
import Client.Model.Resources.Resource;
import Client.Model.User;

import java.util.ArrayList;

public class Siege extends MilitaryUnit {
    ArrayList<Engineer> engineers = new ArrayList<>();
    int engineersNeeded;

    public Siege(String name, int x, int y, int speed, int attack, int defence, int range, User owner, ArrayList<Resource> price, ArrayList<Engineer> engineers) {
        super(name, x, y, speed, attack, defence, range, price, owner);
        this.engineers = engineers;
    }

    public ArrayList<Engineer> getEngineers() {
        return engineers;
    }

    public static MilitaryUnit createUnit(String name, int x, int y, ArrayList<Engineer> engineers, User owner) {
        SiegeType unit = SiegeType.getUnitByName(name);
        if (unit == null) return null;
        int speed = unit.speed;
        int attack = unit.attack;
        int defence = unit.defence;
        int range = unit.range;
        int engineersNeeded = unit.engineersNeeded;
        ArrayList<Resource> price = unit.price;
        return new Siege(name, x, y, speed, attack, defence, range, owner, price, engineers);
    }


}
