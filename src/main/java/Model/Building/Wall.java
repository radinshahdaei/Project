package Model.Building;

import Model.Resources.Resource;
import Model.User;

import java.util.ArrayList;

public class Wall extends Building{
    public Wall(String name, int hp, int x, int y, int workers, ArrayList<Resource> price, User owner) {
        super(name, hp, x, y, workers, price, owner);
    }
}
