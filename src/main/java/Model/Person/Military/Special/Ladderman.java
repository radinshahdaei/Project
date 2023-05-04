package Model.Person.Military.Special;

import Model.Government;
import Model.Person.Military.MilitaryUnit;

import static Model.Resources.Resource.getResources;

public class Ladderman extends MilitaryUnit {
    boolean hasLadder = true;

    public Ladderman(int x, int y, Government military) {
        super("ladderman", x, y, 15, 0, 5, 0, military, getResources("gold", "20"));
    }
}
