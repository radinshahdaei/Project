package Model.Building;

import Model.Resources.Resource;
import Model.User;

import java.util.ArrayList;

import static Model.Resources.Resource.getResources;

public class Moat extends Building {
    public Moat(int x, int y, User owner) {
        super("Moat", 1000000, x, y, 0, getResources(), owner);
    }
}
