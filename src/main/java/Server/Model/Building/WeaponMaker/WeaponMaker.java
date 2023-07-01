package Server.Model.Building.WeaponMaker;

import Client.Model.Building.WeaponMaker.WeaponMakerType;
import Client.Model.Game;
import Client.Model.Resources.Resource;
import Client.Model.User;
import Client.View.InputOutput;
import Client.Model.Building.Building;
import Client.Model.Building.Storage.Storage;

import java.util.ArrayList;
import java.util.HashMap;

import static Client.View.InputOutput.output;

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
        while (true) {
            weapon = printWeapons();
            if (weapon == null) {
                InputOutput.output("You can't buy this weapon!");
            } else {
                break;
            }
        }
        Resource price = weapons.get(weapon);
        Storage stockpile = (Storage) Game.currentGovernment.findBuildingByName("stockpile");
        Storage armoury = (Storage) Game.currentGovernment.findBuildingByName("armoury");
        if (armoury == null) {
            InputOutput.output("You don't have an armoury!");
            return;
        }
        if (!stockpile.removeFromStorage(price)) {
            InputOutput.output("You don't have enough resources!");
            return;
        }
        armoury.addToStorage(weapon);
        InputOutput.output("Weapon bought successfully!");
    }

    public Resource printWeapons() {
        InputOutput.output("What weapon do you want to buy? type it's name!");
        int counter = 1;
        for (Resource weapon : weapons.keySet()) {
            InputOutput.output(counter + ") " + weapon.getResourceType().name);
            counter++;
        }
        String input = InputOutput.input();
        for (Resource weapon : weapons.keySet()) {
            if (weapon.getResourceType().name.equals(input)) {
                return weapon;
            }
        }
        return null;
    }

}
