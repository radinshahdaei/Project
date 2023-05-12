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
import Model.Resources.ResourceModel;

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
        GameMenuController.AllMilitaryUnitsAttack();
        GameMenuController.factoriesProduction();
        GameMenuController.foodDelivery();
        GameMenuController.clearMap();
    }

    private static void AllMilitaryUnitsAttack() {
        for (Government government:GameMenuController.game.getGovernments()) {
            if (government.isDead()) continue;
            for (Person person: government.getPeople()) {
                if (person instanceof MilitaryUnit) ((MilitaryUnit) person).attack();
            }
        }
    }

    public static void factoriesProduction() {
        for (Government government : game.getGovernments()) {
            for (Building building : government.getBuildings()) {
                if(building instanceof Factory) {
                    ((Factory) building).doWork();
                }
            }
        }
    }

    public static void foodDelivery() {
        Government government = Game.currentGovernment;
        int valuePerPerson = (government.getFoodRate() + 2) / 2;
        Storage Granary = (Storage) government.findBuildingByName("granary");
        for (int i = 0; i < government.getPopulation(); ++i) {
            ArrayList<Resource> sortedFoods = government.getResourcesByModel(ResourceModel.FOOD);
            Resource maxFood = sortedFoods.get(0);
            Resource needToDeleted = new Resource(maxFood.getResourceType() , valuePerPerson);
            Granary.removeFromStorage(needToDeleted);
        }

        while(!GovernmentMenuController.checkFoodRate(government.getFoodRate())) {
            government.setFoodRate(government.getFoodRate() - 1);
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
