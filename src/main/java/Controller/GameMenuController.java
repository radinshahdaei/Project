package Controller;

import Model.Building.Building;
import Model.Building.Factory.Factory;
import Model.Building.Storage.Storage;
import Model.Game;
import Model.Government;
import Model.Person.Military.MilitaryUnit;
import Model.Person.Military.Siege.Siege;
import Model.Person.Military.Special.Engineer;
import Model.Person.Person;
import Model.Resources.Resource;

import java.util.ArrayList;

import static View.InputOutput.output;
public class GameMenuController {
    public static Game game;
    public static int mapSize;
    public static void clearMap() {
        for (int i = 0; i < mapSize; ++i) {
            for (int j = 0; j < mapSize; ++j) {
                ArrayList<Person> died = new ArrayList<>();
                for (Person person : game.getMap().getTiles()[i][j].getPeople()) {
                    if(person instanceof MilitaryUnit && ((MilitaryUnit) person).getDefence() == 0) {
                        died.add(person);
                        if(person instanceof Siege)  died.addAll(((Siege) person).getEngineers());
                    }
                }
                for (Person person : died) {
                    GovernmentMenuController.getGovernmentByUser(person.getOwner()).getPeople().remove(person);
                    game.getMap().getTiles()[i][j].getPeople().remove(person);
                }
            }
        }
    }
    public static void nextTurn() {
        GameMenuController.clearMap();
        UnitMenuController.checkPatrols();
        UnitMenuController.moveAllMilitaryUnits();
        GameMenuController.factoriesProduction();
        GameMenuController.clearMap();
    }

    private static void factoriesProduction() {
        for (Government government : game.getGovernments()) {
            for (Building building : government.getBuildings()) {
                if(building instanceof Factory) {
                    ((Factory) building).doWork();
                }
            }
        }
    }

    public static void showResources() {
        int counter = 1;
        for (Building building:Game.currentGovernment.getBuildings()) {
            if (building.getName().equals("stockpile")){
                Storage storage = (Storage) building;
                output("stockpile " + counter + ":");
                for (Resource resource:storage.getStorage()) {
                    output(resource.getResourceType().name + ": " + resource.getCount());
                }
                counter++;
            }
        }
    }
}
