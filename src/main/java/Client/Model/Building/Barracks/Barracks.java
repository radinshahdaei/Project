package Client.Model.Building.Barracks;

import Client.Controller.GameMenuController;
import Client.Model.Game;
import Client.Model.Person.Military.MilitaryUnit;
import Client.Model.Person.Military.Soldier.SoldierType;
import Client.Model.Resources.Resource;
import Client.Model.Resources.ResourceModel;
import Client.Model.User;
import Client.View.InputOutput;
import Client.Model.Building.Building;
import Client.Model.Building.Campfire;
import Client.Model.Building.Storage.Storage;

import java.util.ArrayList;
import java.util.Arrays;

import static Client.View.InputOutput.output;

public class Barracks extends Building {
    ArrayList<String> troops;

    public Barracks(String name, int hp, int x, int y, ArrayList<Resource> price, User owner) {
        super(name, hp, x, y, 0, price, owner);
        setTroops(name);
    }

    public static Building createBuilding(String name, int x, int y, User owner) {
        BarracksType building = BarracksType.getBuildingByName(name);
        if (building == null) return null;
        int hp = building.hp;
        ArrayList<Resource> price = building.price;
        return new Barracks(name, hp, x, y, price, owner);
    }

    public void setTroops(String barracks) {

        if (barracks.equals("barracks")) {
            String[] array = {"archer", "crossbowman", "spearman", "pikeman", "maceman", "swordsman", "knight"};
            this.troops = new ArrayList<>(Arrays.asList(array));
        } else if (barracks.equals("mercenary post")) {
            String[] array = {"arabian archer", "slave", "slinger", "horse archer", "arabian swordsman", "assassin", "fire thrower"};
            this.troops = new ArrayList<>(Arrays.asList(array));
        } else if (barracks.equals("engineer guild")) {
            String[] array = {"engineer", "ladderman"};
            this.troops = new ArrayList<>(Arrays.asList(array));
        } else if (barracks.equals("tunneler guild")) {
            String[] array = {"tunneler"};
            this.troops = new ArrayList<>(Arrays.asList(array));
        }
    }

    public void buyTroop() {
        String troop;
        while (true) {
            troop = printTroops();
            if (troop == null) {
                InputOutput.output("You can't buy this troop!");
            } else {
                break;
            }
        }
        String countString;
        int count;
        InputOutput.output("How many of this troop do you want to buy?");
        while (true) {
            countString = InputOutput.input();
            count = Integer.parseInt(countString);
            if (count > 0) break;
        }
        if (removeStorage(troop, count)) moveToCampfire(troop, count);
    }

    public boolean removeStorage(String troop, int count) {
        ArrayList<Resource> price = getPrice(troop, this.getName());
        Storage stockpile = (Storage) Game.currentGovernment.findBuildingByName("stockpile");
        Storage armoury = (Storage) Game.currentGovernment.findBuildingByName("armoury");
        Storage stable = (Storage) Game.currentGovernment.findBuildingByName("stable");
        if (armoury == null && this.getName().equals("barracks")) {
            InputOutput.output("You don't have an armoury!");
            return false;
        } else if (stable == null && troop.equals("knight")) {
            InputOutput.output("You don't have a stable!");
            return false;
        }
        for (Resource resource : price) {
            resource.setCount(resource.getCount() * count);
            if (resource.getResourceType().getResourceModel().equals(ResourceModel.OTHER)) {
                if (!stockpile.removeFromStorage(resource)) {
                    InputOutput.output("You don't have enough gold!");
                    return false;
                }
            } else if (resource.getResourceType().getResourceModel().equals(ResourceModel.WEAPON)) {
                if (!armoury.removeFromStorage(resource)) {
                    InputOutput.output("You don't have enough weapons or armour!");
                    return false;
                }
            } else if (resource.getResourceType().getResourceModel().equals(ResourceModel.HORSE)) {
                if (!stable.removeFromStorage(resource)) {
                    InputOutput.output("You don't have enough horses!");
                    return false;
                }
            }
        }
        return true;
    }

    public String printTroops() {
        InputOutput.output("Which troop do you want to buy? type it's name!");
        int counter = 1;
        for (String troop : this.troops) {
            InputOutput.output(counter + ") " + troop);
            counter++;
        }
        String input = InputOutput.input();
        if (this.troops.contains(input)) return input;
        return null;

    }

    public ArrayList<Resource> getPrice(String troop, String barracks) {
        if (barracks.equals("barracks") || barracks.equals("mercenary post")) {
            SoldierType soldier = SoldierType.getUnitByName(troop);
            ArrayList<Resource> price = soldier.getPrice();
            return price;
        } else if (barracks.equals("engineer guild") || barracks.equals("tunneler guild")) {
            ArrayList<Resource> price = Resource.getResources("gold", "20");
            return price;
        }
        return null;
    }

    public void moveToCampfire(String troop, int count) {
        Campfire campfire = (Campfire) Game.currentGovernment.findBuildingByName("campfire");
        if (campfire == null) return;
        int x = campfire.getX();
        int y = campfire.getY();
        String type = MilitaryUnit.AllMilitaryUnits.get(troop);
        for (int i = 0 ; i < count ; i++){
            MilitaryUnit militaryUnit = MilitaryUnit.createUnits(troop, type, x, y, Game.currentGovernment.getUser());
            Game.currentGovernment.getPeople().add(militaryUnit);
            GameMenuController.game.getMap().getTiles()[x][y].getPeople().add(militaryUnit);
        }
        InputOutput.output("Troops successfully bought!");
    }
}
