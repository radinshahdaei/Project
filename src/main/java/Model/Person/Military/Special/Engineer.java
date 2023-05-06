package Model.Person.Military.Special;

import Controller.GameMenuController;
import Model.Building.Storage.Storage;
import Model.Game;
import Model.Map;
import Model.Person.Military.MilitaryUnit;
import Model.Person.Military.Siege.Siege;
import Model.Person.Military.Siege.SiegeType;
import Model.Person.Person;
import Model.Resources.Resource;
import Model.Resources.ResourceType;
import Model.User;

import java.util.ArrayList;
import java.util.Arrays;

import static Model.Resources.Resource.createResource;
import static Model.Resources.Resource.getResources;
import static View.InputOutput.input;
import static View.InputOutput.output;

public class Engineer extends MilitaryUnit {
    static ArrayList<String> siegeMachines;

    static {
        String[] array = {"catapult", "trebuchet", "siege tower", "portable shield", "arabic fire ballista", "battering ram"};
        siegeMachines = new ArrayList<>(Arrays.asList(array));
    }

    boolean hasOilPot = false;
    boolean isInSiege = false;
    boolean isInBuilding = false;

    public Engineer(int x, int y, User owner) {
        super("engineer", x, y, 15, 0, 30, 0, getResources("gold", "20"), owner);
    }

    public static MilitaryUnit createUnit(int x, int y, User owner) {
        return new Engineer(x, y, owner);
    }

    public void pourOil(char direction) { //TODO if engineer selected, run if "pour oil -d ~" typed
        if (!getOilPot()) return;
        int x = super.getX();
        int y = super.getY();
        int mapSize = GameMenuController.mapSize;
        if (direction == 'n') {
            y--;
            if (!checkCoordinates(x, y, mapSize)) return;
        } else if (direction == 'e') {
            x++;
            if (!checkCoordinates(x, y, mapSize)) return;
        } else if (direction == 'w') {
            x--;
            if (!checkCoordinates(x, y, mapSize)) return;
        } else if (direction == 's') {
            y++;
            if (!checkCoordinates(x, y, mapSize)) return;
        }
        Map map = GameMenuController.game.getMap();
        map.getTiles()[x][y].setHasOil(true);
    }

    public boolean checkCoordinates(int x, int y, int mapSize) {
        return x >= 0 && y >= 0 && x < mapSize && y < mapSize;
    }


    public void createSiege() { //TODO if engineer selected, run this if "build equipment" typed
        if (Game.currentGovernment.findBuildingByName("siege tent") == null) {
            output("You don't have a siege tent");
            return;
        }
        String machine;
        while (true) {
            machine = printMachines();
            if (machine == null) {
                output("You can't build this machine!");
            } else {
                break;
            }
        }
        int x = super.getX();
        int y = super.getY();
        build(machine, x, y);
    }


    public void build(String machine, int x, int y) {
        SiegeType siegeMachine = SiegeType.getUnitByName(machine);
        if (siegeMachine == null) return;
        Resource goldNeeded = siegeMachine.getGoldNeeded();
        int engineersNeeded = siegeMachine.getEngineersNeeded();
        Storage stockpile = (Storage) Game.currentGovernment.findBuildingByName("stockpile");
        if (!stockpile.removeFromStorage(goldNeeded)) {
            output("You don't have enough gold!");
            return;
        }
        ArrayList<Person> allEngineers = Game.currentGovernment.getEngineers();
        if (allEngineers.size() < engineersNeeded) {
            output("You don't have enough engineers!");
            return;
        }
        ArrayList<Engineer> engineers = new ArrayList<>();
        int i = 0;
        for (Person person : allEngineers) {
            i++;
            engineers.add((Engineer) person);
            ((Engineer) person).goInSiege();
            if (i == engineersNeeded) break;
        }
        MilitaryUnit siege = Siege.createUnit(machine, x, y, engineers, Game.currentGovernment.getUser());
        Game.currentGovernment.getPeople().add(siege);
        GameMenuController.game.getMap().getTiles()[x][y].getPeople().add(siege);
        output("Machine successfully built!");
    }

    public String printMachines() {
        output("Which siege machine do you want to build? type it's name!");
        int counter = 1;
        for (String machine : siegeMachines) {
            output(counter + ") " + machine);
            counter++;
        }
        String input = input();
        if (siegeMachines.contains(input)) return input;
        return null;

    }

    public boolean isAvailable() {
        return !this.hasOilPot && !this.isInSiege;
    }

    public void goInSiege() {
        this.isInSiege = true;
    }

    public void goInBuilding() {
        this.isInBuilding = true;
    }

    public boolean getOilPot() {
        if (this.hasOilPot) return true;
        if (Game.currentGovernment.findBuildingByName("oil smelter") == null) {
            output("You don't have an oil smelter!");
            return false;
        }
        Storage stockPile = (Storage) Game.currentGovernment.findBuildingByName("stockpile");
        if (!stockPile.removeFromStorage(createResource(ResourceType.PITCH, 1))) {
            output("You don't have enough oil!");
            return false;
        }
        this.hasOilPot = true;
        return true;
    }


}
