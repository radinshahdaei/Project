package Model.Person.Military.Soldier;

import Model.Resources.Resource;

import java.util.ArrayList;

import static Model.Resources.Resource.getResources;

public enum SoldierType {
    ARCHER("archer", 15, 15, 30, 10, false, getResources("bow", "1", "gold", "12"), SoldierGuild.EUROPEAN),
    CROSSBOWMAN("crossbowman", 8, 25, 50, 10, false, getResources("crossbow", "1", "leather armor", "1", "gold", "20"), SoldierGuild.EUROPEAN),
    SPEARMAN("spearman", 12, 15, 30, 0, true, getResources("spear", "1", "gold", "8"), SoldierGuild.EUROPEAN),
    PIKEMAN("pikeman", 8, 30, 70, 0, false, getResources("pike", "1", "metal armor", "1", "gold", "20"), SoldierGuild.EUROPEAN),
    MACEMAN("maceman", 12, 35, 50, 0, true, getResources("mace", "1", "leather armor", "1", "gold", "20"), SoldierGuild.EUROPEAN),
    SWORDSMAN("swordsman", 5, 45, 80, 0, false, getResources("sword", "1", "metal armor", "1", "gold", "40"), SoldierGuild.EUROPEAN),
    KNIGHT("knight", 25, 45, 100, 0, false, getResources("sword", "1", "metal armor", "1", "horse", "1", "gold", "40"), SoldierGuild.EUROPEAN),
    // MONK("monk", 10, 15, 30, 0, false, getResources("gold", "10"), null),

    ARABIAN_ARCHER("arabian archer", 15, 15, 30, 10, false, getResources("gold", "75"), SoldierGuild.ARABIAN),
    SLAVE("slave", 15, 10, 5, 0, true, getResources("gold", "5"), SoldierGuild.ARABIAN),
    SLINGER("slinger", 15, 15, 15, 5, true, getResources("gold", "12"), SoldierGuild.ARABIAN),
    HORSE_ARCHER("horse archer", 25, 20, 40, 10, false, getResources("gold", "80"), SoldierGuild.ARABIAN),
    ARABIAN_SWORDSMAN("arabian swordsman", 8, 35, 60, 0, false, getResources("gold", "80"), SoldierGuild.ARABIAN),
    FIRE_THROWER("fire thrower", 15, 25, 30, 5, false, getResources("gold", "100"), SoldierGuild.ARABIAN),
    ASSASSIN("assassin", 15, 15, 30, 0, false, getResources("gold", "60"), SoldierGuild.ARABIAN);

    String name;
    int speed;
    int attack;
    int defence;
    int range;
    boolean canUseLadder;
    ArrayList<Resource> price;
    SoldierGuild type;

    SoldierType(String name, int speed, int attack, int defence, int range, boolean canUseLadder, ArrayList<Resource> price, SoldierGuild type) {
        this.name = name;
        this.speed = speed;
        this.attack = attack;
        this.defence = defence;
        this.range = range;
        this.canUseLadder = canUseLadder;
        this.price = price;
        this.type = type;
    }

    public static SoldierType getUnitByName(String name){
        for (SoldierType unit: SoldierType.values()){
            if (name.equalsIgnoreCase(unit.name)) {
                return unit;
            }
        }
        return null;
    }

    public ArrayList<Resource> getPrice() {
        return price;
    }
}
