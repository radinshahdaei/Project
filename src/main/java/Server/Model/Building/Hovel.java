package Server.Model.Building;

import Client.Model.Building.Building;
import Client.Model.Game;
import Client.Model.Resources.Resource;
import Client.Model.User;

import java.util.ArrayList;

public class Hovel extends Building {
    public Hovel(int x, int y, ArrayList<Resource> price, User owner) {
        super("hovel", 100, x, y, 0, price, owner);
    }

    public static Building createBuilding(int x, int y, User owner) {
        Game.currentGovernment.addPopulation(10);
        // GameMenu.addPopulation(Game.currentGovernment, 8);
        return new Hovel(x, y, Resource.getResources("wood", "6"), owner);
    }
}
