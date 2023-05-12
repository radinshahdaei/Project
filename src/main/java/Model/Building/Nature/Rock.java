package Model.Building.Nature;

import Model.Building.Building;

public class Rock extends Building {
    RockType type;

    public Rock(String name, int x, int y, RockType type) {
        super(name, 500, x, y, 0, null, null);
        this.type = type;
    }

    public static Building createRock(char direction, int x, int y) {
        RockType type = RockType.getRockByDirection(direction);
        String name = String.valueOf(direction) + " rock";
        return new Rock(name, x, y, type);
    }
}
