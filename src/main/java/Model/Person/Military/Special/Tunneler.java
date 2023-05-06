package Model.Person.Military.Special;

import Model.Person.Military.MilitaryUnit;
import Model.User;

import static Model.Resources.Resource.getResources;

public class Tunneler extends MilitaryUnit {
    private boolean underTunnel = false;

    public Tunneler(int x, int y, User owner) {
        super("tunneler", x, y, 15, 15, 50, 0, getResources("gold", "20"), owner);
    }

    public static MilitaryUnit createUnit(int x, int y, User owner) {
        return new Tunneler(x, y, owner);
    }

}
