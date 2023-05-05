package View.Game;

import Controller.UnitMenuController;
import Model.Person.Military.MilitaryUnit;
import Model.Person.Person;
import View.Commands.UnitMenuCommands;

import java.util.ArrayList;
import java.util.regex.Matcher;

import static View.InputOutput.input;
import static View.InputOutput.output;

public class UnitMenu {
    public static ArrayList<MilitaryUnit> userUnitInTile;
    public void run() {
        String command;
        Matcher matcher;
        while (true) {
            command = input();
            if ((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.EXIT)) != null) {
                output("Exited Unit menu");
                return;
            }
            else if ((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.MOVE_UNIT)) != null) {
                int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
                UnitMenuController.moveUnit(x, y);
            }
            else {
                output("Invalid command!");
            }
        }
    }
}
