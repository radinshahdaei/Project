package View.Game;


import Controller.Controller;
import Controller.GameMenuController;
import Controller.MapMenuController;
import Model.Game;
import Model.Government;
import Model.Map;
import Model.User;
import View.Commands.GameMenuCommands;

import java.util.regex.Matcher;

import static View.InputOutput.input;
import static View.InputOutput.output;
public class GameMenu {

    public void run() {
        GameMenuController.game = new Game();
        selectUsers();
        createMap();
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

    private static void createMap() {
        output("Enter 1 if you want a 200 in 200 map or 2 if you want a 400 in 400 map");
        String size = input();
        while (!size.equals("1") && !size.equals("2")) {
            output("Please enter 1 or 2!");
            size = input();
        }
        output("You have created a " + (Integer.parseInt(size) * 200) + " in " + (Integer.parseInt(size) * 200) + " map");
        int mapSize = Integer.parseInt(size) * 200;
        GameMenuController.mapSize = mapSize;
        Map map = new Map();
        GameMenuController.game.setMap(map);
        MapMenuController.initializeMap(mapSize);
        MapMenu mapMenu = new MapMenu();
        mapMenu.run();
    }

    private static void selectUsers() {
        output("Enter the usernames who you want to play with like \"add <username>\" and when you're done type \"Done\"");
        String input;
        Matcher matcher;
        Government government = new Government(Controller.currentUser);
        GameMenuController.game.getGovernments().add(government);
        Game.currentGovernment = government;
        while (true) {
            input = input();
            if (GameMenuCommands.getMatcher(input, GameMenuCommands.DONE) != null) {
                if (GameMenuController.game.getGovernments().size() == 1) {
                    output("You have not selected any other player!");
                    continue;
                }
                output("Selecting users ended!");
                break;
            }
            else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.ADD_USER)) != null) {
                String username = matcher.group("username");
                User user = Controller.findUserByUsername(username);
                if (user == null) {
                    output("User " + username + " not found");
                    continue;
                }
                boolean checkFlag = false;
                for (Government addedGovernment:GameMenuController.game.getGovernments()) {
                    if (addedGovernment.getUser().getUsername().equals(username)) {
                        output("User " + username + " has already been added");
                        checkFlag = true;
                        break;
                    }
                }
                if (checkFlag) continue;
                government = new Government(user);
                GameMenuController.game.getGovernments().add(government);
                output("User " + username + " added");
            }
            else {
                output("Invalid command!");
            }
        }
    }
}
