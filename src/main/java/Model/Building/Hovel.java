package Model.Building;

import Model.Resources.Resource;
import Model.User;

import java.util.ArrayList;

import static Model.Resources.Resource.getResources;

public class Hovel extends Building {
    public Hovel(int x, int y, ArrayList<Resource> price, User owner) {
        super("hovel", 100, x, y, 0, price, owner);
    }

    public static Building createBuilding(int x, int y, User owner) {
        return new Hovel(x, y, getResources("wood", "6"), owner);
    }
}
