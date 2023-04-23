package Model.Building;

import Model.Resource;

import java.util.ArrayList;

import static Model.Resource.getResources;

public enum DefensiveBuildingType {

    LOOKOUT_TOWER("lookout tower", 100, 0, getResources("stone", "15"), 10, 6),
    PERIMETER_TOWER("perimeter tower", 150, 0, getResources("stone", "15"), 8, 5),
    DEFENCE_TURRET("defence turret", 200, 0, getResources("stone", "15"), 8, 5),
    SQUARE_TOWER("square tower", 300, 0, getResources("stone", "35"), 20, 8),
    ROUND_TOWER("round tower", 400, 0, getResources("stone", "40"), 20, 8);


    String name;
    int hp;
    int workers;
    ArrayList<Resource> price = new ArrayList<>();
    int range;
    int damage;


    DefensiveBuildingType(String name, int hp, int workers, ArrayList<Resource> price, int range, int damage) {
        this.name = name;
        this.hp = hp;
        this.workers = workers;
        this.price = price;
        this.range = range;
        this.damage = damage;
    }

    public static DefensiveBuildingType getBuildingByName(String name) {
        for (DefensiveBuildingType building : DefensiveBuildingType.values()) {
            if (name.equalsIgnoreCase(building.name))
                return building;
        }
        return null;
    }

}
