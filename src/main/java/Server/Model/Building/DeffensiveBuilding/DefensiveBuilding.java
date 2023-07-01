package Server.Model.Building.DeffensiveBuilding;

import Client.Model.Building.DeffensiveBuilding.DefensiveBuildingType;
import Client.Model.Resources.Resource;
import Client.Model.User;
import Client.Model.Building.Building;

import java.util.ArrayList;

public class DefensiveBuilding extends Building {
    private int fireRange;
    private int damageAdded;

    public DefensiveBuilding(String name, int hp, int x, int y, int workers, ArrayList<Resource> price, int fireRange, int damageAdded, User owner) {
        super(name, hp, x, y, workers, price, owner);
        this.fireRange = fireRange;
        this.damageAdded = damageAdded;
    }

    public static Building createBuilding(String name, int x, int y, User owner) {
        DefensiveBuildingType building = DefensiveBuildingType.getBuildingByName(name);
        if (building == null) return null;
        int hp = building.hp;
        int workers = building.workers;
        ArrayList<Resource> price = building.price;
        int fireRange = building.range;
        int damageAdded = building.damage;
        return new DefensiveBuilding(name, hp, x, y, workers, price, fireRange, damageAdded, owner);
    }

    public int getFireRange() {
        return fireRange;
    }

    public int getDamageAdded() {
        return damageAdded;
    }
}
