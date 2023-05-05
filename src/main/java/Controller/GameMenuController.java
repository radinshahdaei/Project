package Controller;

import Model.Building.Building;
import Model.Building.Storage.Storage;
import Model.Game;
import Model.Resources.Resource;

import static View.InputOutput.output;
public class GameMenuController {
    public static Game game;
    public static int mapSize;
    public static void nextTurn() {
        UnitMenuController.checkPatrols();
        UnitMenuController.moveAllMilitaryUnits();
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
