package Model.Building;

import Model.User;

public class Campfire extends Building {
    public Campfire(int x, int y, User owner) {
        super("campfire", 500, x, y, 0, null, owner);
    }

    public static Building createBuilding(int x, int y, User owner) {
        return new Campfire(x, y, owner);
    }
}
