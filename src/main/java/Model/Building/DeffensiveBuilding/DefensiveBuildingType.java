package Model.Building.DeffensiveBuilding;

import Model.Resources.Resource;

import java.util.ArrayList;

public enum DefensiveBuildingType {

    LOOKOUT_TOWER("lookout tower", 100, 0, Resource.getResources("stone", "15"), 5, 10),
    PERIMETER_TOWER("perimeter tower", 150, 0, Resource.getResources("stone", "15"), 5, 10),
    DEFENCE_TURRET("defence turret", 200, 0, Resource.getResources("stone", "15"), 3, 10),
    SQUARE_TOWER("square tower", 300, 0, Resource.getResources("stone", "35"), 3, 10),
    ROUND_TOWER("round tower", 400, 0, Resource.getResources("stone", "40"), 3, 10);


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
