package Model.Building.Barracks;

import Model.Resources.Resource;

import java.util.ArrayList;

public enum BarracksType {
    BARRACKS("barracks", 100, Resource.getResources("wood", "15")),
    MERCENARY_POST("mercenary post", 100, Resource.getResources("wood", "10")),
    ENGINEER_GUILD("engineer guild", 100, Resource.getResources("wood", "10", "gold", "100")),
    TUNNELER_GUILD("tunneler guild", 100, Resource.getResources("wood", "10", "gold", "100"));

    String name;
    int hp;
    ArrayList<Resource> price = new ArrayList<>();

    BarracksType(String name, int hp, ArrayList<Resource> price) {
        this.name = name;
        this.hp = hp;
        this.price = price;
    }

    public static BarracksType getBuildingByName(String name) {
        for (BarracksType building : BarracksType.values()) {
            if (name.equalsIgnoreCase(building.name))
                return building;
        }
        return null;
    }
}
