package Server.Model.Building;

import Client.Model.Building.Building;
import Client.Model.Resources.Resource;
import Client.Model.User;

import java.util.ArrayList;

public class Wall extends Building {
    private boolean hasLadder = false;
    private boolean hasStairs = false;
    private boolean hasSiegeTower = false;
    private boolean isGateHouse = false;
    private boolean isCaptured = false;
    private User siegeTowerOwner = null;

    public Wall(String name, int hp, int x, int y, int workers, ArrayList<Resource> price, User owner) {
        super(name, hp, x, y, workers, price, owner);
    }

    public static Building createWall(String name, int x, int y, User owner) {
        if (name.equals("high wall"))
            return new Wall("high wall", 500, x, y, 0, Resource.getResources("stone", "8"), owner);
        else if (name.equals("low wall"))
            return new Wall("high wall", 400, x, y, 0, Resource.getResources("stone", "5"), owner);
        else if (name.equals("small stone gatehouse")) {
            Wall gateHouse = new Wall("small stone gatehouse", 300, x, y, 0, Resource.getResources(), owner);
            gateHouse.setGateHouse(true);
            return gateHouse;
        } else if (name.equals("big stone gatehouse")) {
            Wall gateHouse = new Wall("big stone gatehouse", 500, x, y, 0, Resource.getResources("stone", "20"), owner);
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

    public boolean isHasLadder() {
        return hasLadder;
    }

    public boolean isHasStairs() {
        return hasStairs;
    }

    public boolean isHasSiegeTower() {
        return hasSiegeTower;
    }

    public boolean isGateHouse() {
        return isGateHouse;
    }

    public boolean isCaptured() {
        return isCaptured;
    }

    public User getSiegeTowerOwner() {
        return siegeTowerOwner;
    }
}
