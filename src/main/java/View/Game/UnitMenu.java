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
            else if ((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.SET_STATUS)) != null) {
                String status = matcher.group("status");
                UnitMenuController.setStatus(status);
            }
            else if ((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.ATTACK_ENEMY)) != null) {
                int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
                UnitMenuController.attackEnemy(x, y);
            }
            else if ((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.ATTACH_ARCHER)) != null) {
                int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
                UnitMenuController.attackArcher(x, y);
            }
            else if ((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.DISBAND)) != null) {
                UnitMenuController.disbandUnit();
            }
            else if ((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.PATROL_UNIT)) != null) {
                int x1 = Integer.parseInt(matcher.group("X1")), y1 = Integer.parseInt(matcher.group("Y1")),
                        x2 = Integer.parseInt(matcher.group("X2")), y2 = Integer.parseInt(matcher.group("Y2"));
                if (UnitMenuController.patrolUnit(x1, x2, y1, y2)) {
                    output("Exiting Unit Menu");
                    return;
                }
            }
            else if ((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.POUR_OIL)) != null) {
                char direction = matcher.group("direction").charAt(0);
                UnitMenuController.pourOil(direction);
            }
            else if ((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.BUILD_EQUIPMENT)) != null) {
                UnitMenuController.buildEquipment();
                if (userUnitInTile.size() == 0) {
                    output("There are no more units remaining in this tile\nexiting!");
                    return;
                }
            }
            else {
                output("Invalid command!");
            }
        }
    }
}
