package Model.Building.Storage;

import Model.Resources.Resource;
import Model.Resources.ResourceModel;

import java.util.ArrayList;

import static Model.Resources.Resource.getResources;
import static Model.Resources.Resource.getResourcesByType;

public enum StorageType {

    GRANARY("granary", 300, 0, getResources("wood", "5"), getResourcesByType(ResourceModel.FOOD, 0), 1000),
    ARMOURY("armoury", 300, 0, getResources("wood", "5"), getResourcesByType(ResourceModel.WEAPON, 0), 100),
    STOCKPILE("stockpile", 300, 0, getResources(), getResourcesByType(ResourceModel.OTHER, 0), 10000),
    STABLE("stable", 300, 0, getResources("wood", "20", "gold", "400"), getResourcesByType(ResourceModel.HORSE, 4), 4);

    String name;
    int hp;
    int workers;
    ArrayList<Resource> price = new ArrayList<>();
    ArrayList<Resource> storage = new ArrayList<>();
    int maxCapacity;

    StorageType(String name, int hp, int workers, ArrayList<Resource> price, ArrayList<Resource> storage, int maxCapacity) {
        this.name = name;
        this.hp = hp;
        this.workers = workers;
        this.price = price;
        this.storage = storage;
        this.maxCapacity = maxCapacity;
    }

    public static StorageType getBuildingByName(String name) {
        for (StorageType building : StorageType.values()) {
            if (name.equalsIgnoreCase(building.name))
                return building;
        }
        return null;
    }
}
