package Model.Building;

import Controller.GameMenuController;
import Model.Game;
import Model.Person.Military.Special.Engineer;
import Model.Person.Person;
import Model.Resources.Resource;
import Model.User;

import java.util.ArrayList;

import static Model.Resources.Resource.getResources;
import static View.InputOutput.output;

public class EnginnerBuilding extends Building {
    Engineer engineer;

    public EnginnerBuilding(String name, int hp, int x, int y, ArrayList<Resource> price, User owner, Engineer engineer) {
        super(name, hp, x, y, 0, price, owner);
        this.engineer = engineer;
    }

    public static Building createBuilding(String name, int x, int y, User owner) {
        ArrayList<Person> allEngineers = Game.currentGovernment.getEngineers();
        Engineer engineer = null;
        for (Person person : allEngineers) {
            if (((Engineer) person).isAvailable()) {
                engineer = (Engineer) person;
                engineer.goInBuilding();
                GameMenuController.game.getMap().getTiles()[((Engineer) person).getX()][((Engineer) person).getY()].getPeople().remove(person);
                break;
            }
        }
        if (engineer == null) {
            output("You don't have an engineer");
            return null;
        }
        if (name.equalsIgnoreCase("siege tent")) {
            return new EnginnerBuilding("siege tent", 100, x, y, getResources(), owner, engineer);
        } else if (name.equalsIgnoreCase("oil smelter")) {
            return new EnginnerBuilding("oil smelter", 100, x, y, getResources(), owner, engineer);
        }
        return null;
    }


}
