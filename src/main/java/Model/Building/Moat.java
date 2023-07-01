package Model.Building;

import Model.User;

public class Moat extends Building {
    public Moat(int x, int y, User owner) {
        super("Moat", 1000000, x, y, 0, null, owner);
    }
}
