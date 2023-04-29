package View.Game;


import Controller.Controller;
import Controller.GameMenuController;
import Controller.MapMenuController;
import Controller.BuildingMenuController;
import Model.Building.Building;
import Model.Building.BuildingType;
import Model.Building.Storage;
import Model.Game;
import Model.Government;
import Model.Map;
import Model.Resources.Resource;
import Model.Resources.ResourceType;
import Model.User;
import View.Commands.GameMenuCommands;

import java.util.ArrayList;
import java.util.regex.Matcher;

import static View.InputOutput.input;
import static View.InputOutput.output;
public class GameMenu {
    private static final int[] defaultXPositions = new int[5];
    private static final int[] defaultYPositions = new int[5];


    public void run() {
        GameMenuController.game = new Game();
        createMap();
        selectUsers();
        String command;
        Matcher matcher;
        while (true) {
            for (Government government:GameMenuController.game.getGovernments()){
                Game.currentGovernment = government;
                output("Currently " + government.getUser().getUsername() + " is playing");
                while (true) {
                    command = input();
                    if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.END)) != null) {
                        output("Game ended manually");
                        return;
                    }
                    else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.NEXT_TURN)) != null) {
                        GameMenuController.nextTurn();
                        break;
                    }
                    else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.CLEAR)) != null) {
                        int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
                        MapMenuController.clear(x , y);
                    }
                    else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.DROP_BUILDING)) != null) {
                        int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
                        String type = Controller.removeDoubleQuote(matcher.group("type"));
                        BuildingMenuController.dropBuilding(x, y, type);
                    }
                    else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SELECT_BUILDING)) != null) {
                        int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
                        BuildingMenuController.selectBuilding(x, y);
                    }
                    else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.DRAW_MAP)) != null) {
                        int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
                        MapMenu.movingMap(x, y);
                    }
                    else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_RESOURCES)) != null) {
                        GameMenuController.showResources();
                    }
                    else {
                        output("Invalid command!");
                    }
                }
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
        valueDefaults();
        Map map = new Map();
        GameMenuController.game.setMap(map);
        MapMenuController.initializeMap(mapSize);
        MapMenu mapMenu = new MapMenu();
        mapMenu.run();
    }

    private static void selectUsers() {
        output("Enter the usernames who you want to play with like \"add <username>\" and when you're done type \"Done\"");
        int counter = 0;
        String input;
        Matcher matcher;
        Government government = new Government(Controller.currentUser);
        GameMenuController.game.getGovernments().add(government);
        Game.currentGovernment = government;
        giveDefaultBuildings(government, counter);
        giveDefaultResources(government, (Storage) government.getBuildings().get(0));
        counter++;
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
                giveDefaultBuildings(government, counter);
                giveDefaultResources(government, (Storage) government.getBuildings().get(0));
                counter++;
                output("User " + username + " added");
            }
            else {
                output("Invalid command!");
            }
        }
    }
    private static void valueDefaults() {
        int k = GameMenuController.mapSize;
        defaultXPositions[0] = 0;
        defaultXPositions[1] = k - 1;
        defaultXPositions[2] = 0;
        defaultXPositions[3] = k - 1;
        defaultXPositions[4] = k / 2;
        defaultYPositions[0] = 0;
        defaultYPositions[1] = 0;
        defaultYPositions[2] = k - 1;
        defaultYPositions[3] = k - 1;
        defaultYPositions[4] = k / 2;
    }
    private static void giveDefaultBuildings(Government government, int counter) {
        BuildingType buildingType = Building.ALL_BUILDINGS.get("stockpile");
        Building building = Building.createBuildings("stockpile", defaultXPositions[counter],
                defaultYPositions[counter], buildingType);
        government.getBuildings().add(building);
        GameMenuController.game.getMap().getTiles()[defaultXPositions[counter]][defaultYPositions[counter]].
                setBuilding(building);
    }
    private static void giveDefaultResources(Government government, Storage storage) {
        Resource resource;
        resource = Resource.createResource(ResourceType.getResourceByName("wood"), 100);
        storage.addToStorage(resource);
        government.getResources().add(resource);
        resource = Resource.createResource(ResourceType.getResourceByName("stone"), 100);
        storage.addToStorage(resource);
        government.getResources().add(resource);
        resource = Resource.createResource(ResourceType.getResourceByName("iron"), 50);
        storage.addToStorage(resource);
        government.getResources().add(resource);
        resource = Resource.createResource(ResourceType.getResourceByName("wheat"), 200);
        storage.addToStorage(resource);
        government.getResources().add(resource);
    }
}
