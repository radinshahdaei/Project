package Client.Model.Building;

import Client.Controller.GameMenuController;
import Client.Model.Building.Storage.Storage;
import Client.Model.Game;
import Client.Model.Person.Military.MilitaryUnit;
import Client.Model.Resources.Resource;
import Client.Model.Resources.ResourceType;
import Client.Model.User;
import Client.View.InputOutput;

import java.util.ArrayList;

import static Client.View.InputOutput.output;

public class Church extends Building {
    public Church(String name, int hp, int x, int y, ArrayList<Resource> price, User owner) {
        super(name, hp, x, y, 0, price, owner);
    }

    public static Building createBuilding(String name, int x, int y, User owner) {
        if (name.equalsIgnoreCase("church")) {
            return new Church("church", 200, x, y, Resource.getResources("gold", "500"), owner);
        } else if (name.equalsIgnoreCase("cathedral")) {
            return new Church("cathedral", 300, x, y, Resource.getResources("gold", "1000"), owner);
        }
        return null;
    }

    public void buyMonk() {
        String troop;
        while (true) {
            InputOutput.output("Which troop do you want to buy? type it's name!\n" +
                    "1) monk");
            troop = InputOutput.input();
            if (!troop.equals("monk")) {
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
        Resource resource = Resource.createResource(ResourceType.GOLD, 10 * count);
        Storage stockpile = (Storage) Game.currentGovernment.findBuildingByName("stockpile");
        if (!stockpile.removeFromStorage(resource)) {
            InputOutput.output("You don't have enough gold!");
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
        InputOutput.output("Troops successfully bought!");
    }
}
