package Model.Building;

import Model.Game;
import Model.Resources.Resource;
import Model.User;

import java.util.ArrayList;
import java.util.HashMap;

import static View.InputOutput.input;
import static View.InputOutput.output;

public class WeaponMaker extends Building {
    HashMap<Resource, Resource> weapons = new HashMap<>();

    public WeaponMaker(String name, int hp, int x, int y, int workers, ArrayList<Resource> price, HashMap<Resource, Resource> weapons, User owner) {
        super(name, hp, x, y, workers, price, owner);
        this.weapons = weapons;
    }

<<<<<<< HEAD
    public static Building createBuilding(String name, int x, int y) {
=======
    public static Building createBuilding(String name, int x, int y, User owner) {
>>>>>>> alireza
        WeaponMakerType building = WeaponMakerType.getBuildingByName(name);
        if (building == null) return null;
        int hp = building.hp;
        int workers = building.workers;
        ArrayList<Resource> price = building.price;
        HashMap<Resource, Resource> weapons = building.weapons;
        return new WeaponMaker(name, hp, x, y, workers, price, weapons, owner);
    }

    public void buyWeapon() {
        Resource weapon;
        while (true) {
            weapon = printWeapons();
            if (weapon == null) {
                output("You can't buy this weapon!");
            } else {
                break;
            }
        }
        Resource price = weapons.get(weapon);
        Storage stockpile = (Storage) Game.currentGovernment.findBuildingByName("stockpile");
        Storage armoury = (Storage) Game.currentGovernment.findBuildingByName("armoury");
        if (stockpile == null || armoury == null) return;
        stockpile.removeFromStorage(price);
        armoury.addToStorage(weapon);


    }

    public Resource printWeapons() {
        output("What weapon do you want to buy? type it's name!");
        int counter = 1;
        for (Resource weapon : weapons.keySet()) {
            output(counter + ": " + weapon.getResourceType().name);
            counter++;
        }
        String input = input();
        for (Resource weapon : weapons.keySet()) {
            if (weapon.getResourceType().name.equals(input)) {
                return weapon;
            }
        }
        return null;
    }

}
