package Server.Model.Building;

import Client.Controller.GameMenuController;
import Client.Model.Building.Building;
import Client.Model.Game;
import Client.Model.Person.Military.Special.Engineer;
import Client.Model.Person.Person;
import Client.Model.Resources.Resource;
import Client.Model.User;
import Client.View.InputOutput;

import java.util.ArrayList;

import static Client.View.InputOutput.output;

public class EnginnerBuilding extends Client.Model.Building.Building {
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
            InputOutput.output("You don't have an engineer");
            return null;
        }
        if (name.equalsIgnoreCase("siege tent")) {
            return new EnginnerBuilding("siege tent", 100, x, y, Resource.getResources(), owner, engineer);
        } else if (name.equalsIgnoreCase("oil smelter")) {
            return new EnginnerBuilding("oil smelter", 100, x, y, Resource.getResources(), owner, engineer);
        }
        return null;
    }


}
