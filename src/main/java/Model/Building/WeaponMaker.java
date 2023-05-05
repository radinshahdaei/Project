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
    public static Building createBuilding(String name, int x, int y, User owner) {
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
        weapon = printWeapons();
        if (weapon == null) {
            output("Cancelled");
            return;
        }
        if (isSure()) return;
        Resource price = weapons.get(weapon);
        Storage stockpile = (Storage) Game.currentGovernment.findBuildingByName("stockpile");
        Storage armoury = (Storage) Game.currentGovernment.findBuildingByName("armoury");
        if (armoury == null) {
            output("You don't have an armoury!");
            return;
        }
        if (!stockpile.removeFromStorage(price)) {
            output("You don't have enough resources!");
            return;
        }
        armoury.addToStorage(weapon);
        output("Weapon added to your armoury");
    }

    private static boolean isSure() {
        output("Are you Sure?(Y/N)");
        String input = input();
        while (true) {
            if (input.equals("N")) {
                output("Exited Weapon Maker!!!");
                return true;
            }
            if (input.equals("Y")) {
                break;
            }
            output("Please enter Y or N!");
            input = input();
        }
        return false;
    }

    public Resource printWeapons() {
        output("What weapon do you want to buy? type it's name!");
        int counter = 1;
        for (Resource weapon : weapons.keySet()) {
            output(counter + ") "+ weapon.getResourceType().name);
            counter++;
        }
        while (true){
            String input = input();
            if (input.equals("cancel")) return null;
            for (Resource weapon : weapons.keySet()) {
                if (weapon.getResourceType().name.equals(input)) {
                    return weapon;
                }
            }
            output("Invalid weapon name");
        }
    }

}
