package Model.Building;

import Model.Resources.Resource;
import Model.User;

import java.util.ArrayList;

import static Model.Resources.Resource.getResources;

public class Wall extends Building {
    boolean canPass = false;

    public Wall(String name, int hp, int x, int y, int workers, ArrayList<Resource> price, User owner) {
        super(name, hp, x, y, workers, price, owner);
    }

    public static Building createWall(String name, int x, int y, User owner) {
        if (name.equals("high wall"))
            return new Wall("high wall", 500, x, y, 0, getResources("stone", "8"), owner);
        else if (name.equals("low wall"))
            return new Wall("high wall", 400, x, y, 0, getResources("stone", "5"), owner);
        return null;
    }

    public void pass() {
        this.canPass = true;
    }

}
