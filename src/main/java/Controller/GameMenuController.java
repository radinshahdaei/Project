package Controller;

import Model.Building.Building;
import Model.Building.Factory.Factory;
import Model.Building.Keep;
import Model.Building.Storage.Storage;
import Model.Building.Wall;
import Model.Game;
import Model.Government;
import Model.Person.Military.MilitaryUnit;
import Model.Person.Military.Siege.Siege;
import Model.Person.Military.Special.Engineer;
import Model.Person.Military.Special.Ladderman;
import Model.Person.Person;
import Model.Resources.Resource;
import Model.Resources.ResourceModel;
import Model.Resources.ResourceType;
import View.Game.GameMenu;

import java.awt.font.GlyphVector;
import java.util.ArrayList;

import static Model.Game.currentGovernment;
import static View.InputOutput.output;
public class GameMenuController {
    public static Game game;
    public static int mapSize;
    public static void clearMap() {
        for (int i = 0; i < mapSize; ++i) {
            for (int j = 0; j < mapSize; ++j) {
                ArrayList<Person> died = new ArrayList<>();
                for (Person person : game.getMap().getTiles()[i][j].getPeople()) {
                    if(person instanceof MilitaryUnit && ((MilitaryUnit) person).getDefence() <= 0) {
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
        GameMenuController.onFire();
        GameMenuController.clearMap();
        UnitMenuController.checkPatrols();
        UnitMenuController.attackWithStatus();
        UnitMenuController.moveAllMilitaryUnits();
        GameMenuController.putLadderAndSiege();
        GameMenuController.AllMilitaryUnitsAttack();
        GameMenuController.factoriesProduction();
        GameMenuController.foodDelivery();
        GameMenuController.getTaxes();
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
        if (Granary == null) {
            output("You do not have a Granary to feed your fucking people you stupid bitch!");
            return;
        }
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

    public static void getTaxes() {
        Government government = Game.currentGovernment;
        Storage stockpile = (Storage) government.findBuildingByName("stockpile");
        if(government.getTaxRate() < 0) {
            int valuePerPerson = 10 - (government.getTaxRate() + 3) * 2;
            int totalGoldNeeded = (valuePerPerson * currentGovernment.getPopulation()) / 10;
            Resource needToDeleted = new Resource(ResourceType.GOLD , totalGoldNeeded);
            stockpile.removeFromStorage(needToDeleted);
        }
        else {
            int valuePerPerson = 20 - (government.getTaxRate() - 8) * 2;
            int totalGoldReceived = (valuePerPerson * currentGovernment.getPopulation()) / 10;
            Resource needToAdded = new Resource(ResourceType.GOLD , totalGoldReceived);
            stockpile.addToStorage(needToAdded);
        }
    }

    public static void onFire() {
        for (Person person : currentGovernment.getPeople()) {
            if(person instanceof MilitaryUnit &&
                    game.getMap().getTiles()[((MilitaryUnit) person).getX()][((MilitaryUnit) person).getY()].isOnFire()) {
                ((MilitaryUnit) person).reduceDefence(20);
            }
        }
        for (Building building: currentGovernment.getBuildings()) {
            if (game.getMap().getTiles()[building.getX()][building.getY()].isOnFire()) {
                building.reduceHP(20);
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

    public static void putLadderAndSiege() {
        for (Government government:GameMenuController.game.getGovernments()) {
            for (Person person:government.getPeople()) {
                if (!(person instanceof MilitaryUnit)) continue;
                MilitaryUnit militaryUnit = (MilitaryUnit) person;
                Building building;
                if ((building = GameMenuController.game.getMap().getTiles()[militaryUnit.getX()]
                        [militaryUnit.getY()].getBuilding()) != null){
                    if (building instanceof Wall && person instanceof Ladderman) {
                        ((Wall) building).setHasLadder(true);
                    }
                    if (building instanceof Wall && person.getName().equals("siege tower")) {
                        ((Wall) building).setHasSiegeTower(true);
                        ((Wall) building).setSiegeTowerOwner(militaryUnit.getOwner());
                    }
                    if ((building.getName().equals("small stone gatehouse") || building.getName().equals("big stone gatehouse"))
                            && !building.getOwner().equals(militaryUnit.getOwner())) {
                        assert building instanceof Wall;
                        ((Wall) building).setCaptured(true);
                    }
                }
            }
        }
    }

    public static boolean checkAllGovernmentsDead() {
        int counter = 0;
        for (Government government:GameMenuController.game.getGovernments()) {
            if (government.isDead()) counter++;
        }
        if (counter <= 1) return false;
        return true;
    }

    public static void countScores() {
        int score = 0;
        for (Government government:GameMenuController.game.getGovernments()) {
            score = 0;
            if (government.isDead()) {
                score += 100;
                score += (government.getPopularity() * GameMenuController.game.getGovernments().size()) / GameMenu.numberOfTurns;
                score += government.getResourceByType(ResourceType.GOLD).getCount() / 10;
                government.getUser().addHighScore(score);
            }
            else {
                score += 500;
                score += (government.getPopularity() * GameMenuController.game.getGovernments().size()) / GameMenu.numberOfTurns;
                score += government.getResourceByType(ResourceType.GOLD).getCount() / 10;
                for (Building building:government.getBuildings()) {
                    if (building instanceof Keep) score += building.getHp();
                }
                government.getUser().addHighScore(score);
                output("User " + government.getUser().getUsername() + " is the winner");
            }
        }
    }
}
