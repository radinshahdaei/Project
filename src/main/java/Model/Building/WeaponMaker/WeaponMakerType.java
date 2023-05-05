package Model.Building.WeaponMaker;

import Model.Resources.Resource;

import java.util.ArrayList;
import java.util.HashMap;

import static Model.Resources.Resource.getResources;
import static Model.Resources.Resource.getWeapons;

public enum WeaponMakerType {
    ARMOURER("armourer", 100, 1, getResources("wood", "20", "gold", "100"), getWeapons("metal armour", "1", "iron", "1")),
    TANNER("tanner", 100, 1, getResources("wood", "20", "gold", "100"), getWeapons("leather armor", "3", "cow", "1")),
    FLETCHER("fletcher", 100, 1, getResources("wood", "20", "gold", "100"), getWeapons("bow", "1", "wood", "2", "crossbow", "1", "wood", "3")),
    POLETURNER("poleturner", 100, 1, getResources("wood", "10", "gold", "100"), getWeapons("spear", "1", "wood", "1", "pike", "1", "wood", "2")),
    BLACKSMITH("blacksmith", 100, 1, getResources("wood", "20", "gold", "100"), getWeapons("mace", "1", "iron", "1", "sword", "1", "iron", "1"));

    String name;
    int hp;
    int workers;
    ArrayList<Resource> price = new ArrayList<>();
    HashMap<Resource, Resource> weapons = new HashMap<>();

    WeaponMakerType(String name, int hp, int workers, ArrayList<Resource> price, HashMap<Resource, Resource> weapons) {
        this.name = name;
        this.hp = hp;
        this.workers = workers;
        this.price = price;
        this.weapons = weapons;
    }

    public static WeaponMakerType getBuildingByName(String name) {
        for (WeaponMakerType building : WeaponMakerType.values()) {
            if (name.equalsIgnoreCase(building.name))
                return building;
        }
        return null;
    }
}
