package Server.Model.Person.Military.Special;

import Client.Model.Person.Military.MilitaryUnit;
import Client.Model.User;

import static Client.Model.Resources.Resource.getResources;

public class Ladderman extends MilitaryUnit {
    boolean hasLadder = true;

    public Ladderman(int x, int y, User owner) {
        super("ladderman", x, y, 15, 0, 5, 0, getResources("gold", "20"), owner);
    }
    //TODO ladderman can have wall destination


    public static MilitaryUnit createUnit(int x, int y, User owner) {
        return new Ladderman(x, y, owner);
    }

    
}

