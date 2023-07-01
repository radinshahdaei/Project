package Client.Model.Building;

import Client.Model.Game;
import Client.Model.User;

public class Keep extends Building {
    public Keep(int x, int y, User owner) {
        super("keep", 500, x, y, 0, null, owner);
    }

    public static Building createBuilding(int x, int y, User owner) {
        Game.getGovernmentByUser(owner).addPopulation(10);
        // GameMenu.addPopulation(Game.currentGovernment, 10);
        return new Keep(x, y, owner);
    }

}
