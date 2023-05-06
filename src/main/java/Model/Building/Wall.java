package Model.Building;

import Model.Resources.Resource;
import Model.User;

import java.util.ArrayList;

import static Model.Resources.Resource.getResources;

public class Wall extends Building {
    boolean hasLadder = false;
    boolean hasStairs = false;
    boolean hasSiegeTower = false;
    boolean isGateHouse = false;
    boolean isCaptured = false;
    User siegeTowerOwner = null;

    public Wall(String name, int hp, int x, int y, int workers, ArrayList<Resource> price, User owner) {
        super(name, hp, x, y, workers, price, owner);
    }

    public static Building createWall(String name, int x, int y, User owner) {
        if (name.equals("high wall"))
            return new Wall("high wall", 500, x, y, 0, getResources("stone", "8"), owner);
        else if (name.equals("low wall"))
            return new Wall("high wall", 400, x, y, 0, getResources("stone", "5"), owner);
        else if (name.equals("small stone gatehouse")) {
            Wall gateHouse = new Wall("small stone gatehouse", 300, x, y, 0, getResources(), owner);
            gateHouse.setGateHouse(true);
            return gateHouse;
        } else if (name.equals("big stone gatehouse")) {
            Wall gateHouse = new Wall("big stone gatehouse", 500, x, y, 0, getResources("stone", "20"), owner);
            gateHouse.setGateHouse(true);
            return gateHouse;
        }
        return null;
    }

    public void setHasLadder(boolean hasLadder) {
        this.hasLadder = hasLadder;
    }

    public void setHasStairs(boolean hasStairs) {
        this.hasStairs = hasStairs;
    }

    public void setHasSiegeTower(boolean hasSiegeTower) {
        this.hasSiegeTower = hasSiegeTower;
    }

    public void setGateHouse(boolean gateHouse) {
        isGateHouse = gateHouse;
    }

    public void setCaptured(boolean captured) {
        isCaptured = captured;
    }

    public void setSiegeTowerOwner(User siegeTowerOwner) {
        this.siegeTowerOwner = siegeTowerOwner;
    }
}
