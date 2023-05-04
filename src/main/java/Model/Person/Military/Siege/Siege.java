package Model.Person.Military.Siege;

import Model.Government;
import Model.Person.Military.MilitaryUnit;
import Model.Person.Military.Special.Engineer;
import Model.Resources.Resource;

import java.util.ArrayList;

public class Siege extends MilitaryUnit {
    ArrayList<Engineer> engineers = new ArrayList<>();
    int engineersNeeded;

    public Siege(String name, int x, int y, int speed, int attack, int defence, int range, Government military, ArrayList<Resource> price, int engineersNeeded) {
        super(name, x, y, speed, attack, defence, range, military, price);
        this.engineersNeeded = engineersNeeded;
    }
}
