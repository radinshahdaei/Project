package Model.Person.Military.Special;

import Model.Government;
import Model.Person.Military.MilitaryUnit;
import Model.User;

import static Model.Resources.Resource.getResources;

public class Ladderman extends MilitaryUnit {
    boolean hasLadder = true;

    public Ladderman(int x, int y, User owner) {
        super("ladderman", x, y, 15, 0, 5, 0, getResources("gold", "20"), owner);
    }


    public static MilitaryUnit createUnit(int x,int y,User owner){
        return new Ladderman(x,y,owner);
    }
}
