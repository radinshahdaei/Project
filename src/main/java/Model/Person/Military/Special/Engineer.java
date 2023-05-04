package Model.Person.Military.Special;

import Model.Government;
import Model.Person.Military.MilitaryUnit;
import Model.User;

import static Model.Resources.Resource.getResources;

public class Engineer extends MilitaryUnit {
    boolean hasOilPot = false;
    boolean isInSiege = false;

    public Engineer(int x, int y, User owner) {
        super("engineer", x, y, 15, 0, 30, 0, getResources("gold", "20"), owner);
    }

    public static MilitaryUnit createUnit(int x,int y,User owner){
        return new Engineer(x,y,owner);
    }
}
