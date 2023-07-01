package Server.Model.Building;

import Client.Model.Building.Building;
import Client.Model.User;

public class Campfire extends Client.Model.Building.Building {
    public Campfire(int x, int y, User owner) {
        super("campfire", 500, x, y, 0, null, owner);
    }

    public static Building createBuilding(int x, int y, User owner) {
        return new Campfire(x, y, owner);
    }
}
