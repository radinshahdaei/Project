package Model.Building;

import Controller.GameMenuController;
import Model.Building.Storage.Storage;
import Model.Game;
import Model.Person.Military.MilitaryUnit;
import Model.Resources.Resource;
import Model.Resources.ResourceType;
import Model.User;

import java.util.ArrayList;

import static Model.Resources.Resource.createResource;
import static Model.Resources.Resource.getResources;
import static View.InputOutput.input;
import static View.InputOutput.output;

public class Church extends Building {
    public Church(String name, int hp, int x, int y, ArrayList<Resource> price, User owner) {
        super(name, hp, x, y, 0, price, owner);
    }

    public static Building createBuilding(String name, int x, int y, User owner) {
        if (name.equalsIgnoreCase("church")) {
            return new Church("church", 200, x, y, getResources("gold", "500"), owner);
        } else if (name.equalsIgnoreCase("cathedral")) {
            return new Church("cathedral", 300, x, y, getResources("gold", "1000"), owner);
        }
        return null;
    }

    public void buyMonk() {
        String troop;
        while (true) {
            output("Which troop do you want to buy? type it's name!\n" +
                    "1) monk");
            troop = input();
            if (!troop.equals("monk")) {
                output("You can't buy this troop!");
            } else {
                break;
            }
        }
        String countString;
        int count;
        output("How many of this troop do you want to buy?");
        while (true) {
            countString = input();
            count = Integer.parseInt(countString);
            if (count > 0) break;
        }
        Resource resource = createResource(ResourceType.GOLD, 10 * count);
        Storage stockpile = (Storage) Game.currentGovernment.findBuildingByName("stockpile");
        if (!stockpile.removeFromStorage(resource)) {
            output("You don't have enough gold!");
            return;
        }
        Campfire campfire = (Campfire) Game.currentGovernment.findBuildingByName("campfire");
        if (campfire == null) return;
        int x = campfire.getX();
        int y = campfire.getY();
        String type = MilitaryUnit.AllMilitaryUnits.get(troop);
        for (int i = 0; i < count; i++) {
            MilitaryUnit militaryUnit = MilitaryUnit.createUnits(troop, type, x, y, Game.currentGovernment.getUser());
            Game.currentGovernment.getPeople().add(militaryUnit);
            GameMenuController.game.getMap().getTiles()[x][y].getPeople().add(militaryUnit);
        }
        output("Troops successfully bought!");
    }
}
