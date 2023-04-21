package View.Game;


import Controller.GameMenuController;
import Controller.MapMenuController;
import Model.Game;
import Model.Map;
import View.Commands.GameMenuCommands;

import java.util.regex.Matcher;

import static View.InputOutput.input;
import static View.InputOutput.output;
public class GameMenu {

    public void run() {
        output("Enter 1 if you want a 200 in 200 map or 2 if you want a 400 in 400 map");
        String size = input();
        while (!size.equals("1") && !size.equals("2")) {
            output("Please enter 1 or 2!");
            size = input();
        }
        int mapSize = Integer.parseInt(size) * 200;
        GameMenuController.game = new Game();
        GameMenuController.mapSize = mapSize;
        Map map = new Map();
        GameMenuController.game.setMap(map);
        MapMenuController.initializeMap(mapSize);
        MapMenu mapMenu = new MapMenu();
        mapMenu.run();
        String command;
        Matcher matcher;
        while (true) {
            command = input();
            if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.END)) != null) {
                output("Game ended manually");
                return;
            }
            else {
                output("Invalid command!");
            }
        }
    }
}
