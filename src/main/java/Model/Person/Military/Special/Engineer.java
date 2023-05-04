package Model.Person.Military.Special;

import Model.Government;
import Model.Person.Military.MilitaryUnit;

import static Model.Resources.Resource.getResources;

public class Engineer extends MilitaryUnit {
    boolean hasOilPot = false;
    boolean isInSiege = false;

    public Engineer(int x, int y, Government military) {
        super("engineer", x, y, 15, 0, 30, 0, military, getResources("gold", "20"));
    }
}
