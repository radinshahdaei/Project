package Model.Person.Military.Special;

import Model.Government;
import Model.Person.Military.MilitaryUnit;

import static Model.Resources.Resource.getResources;

public class Tunneler extends MilitaryUnit {
    public Tunneler(int x, int y, Government military) {
        super("tunneler", x, y, 15, 15, 50, 0, military, getResources("gold", "20"));
    }
}
