package Model.Building;

import Model.Resources.Resource;
import Model.User;

import java.util.ArrayList;

public class Keep extends Building{
    public Keep(int x, int y, User owner) {
        super("keep", 500, x, y, 0, null, owner);
    }
    public static Building createBuilding(int x,int y,User owner){
        return new Keep(x,y, owner);
    }

}
