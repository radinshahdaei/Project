package View.Game;


import Controller.*;
import Model.Building.Building;
import Model.Building.BuildingType;
import Model.Building.Storage.Storage;
import Model.Game;
import Model.Government;
import Model.Map;
import Model.Person.Person;
import Model.Person.PersonType;
import Model.Resources.Resource;
import Model.Resources.ResourceType;
import Model.User;
import View.Commands.GameMenuCommands;
import View.Commands.MapMenuCommands;

import java.util.regex.Matcher;

import static View.InputOutput.input;
import static View.InputOutput.output;

public class GameMenu {
    private static final int[] defaultXPositions = new int[20];
    private static final int[] defaultYPositions = new int[20];
    public static int numberOfTurns;


    boolean startManually = false;
    public void run() {

        if (startManually) {
            GameMenuController.game = new Game();
            createMap();
            selectUsers();
        }
        numberOfTurns = 0;
        String command;
        Matcher matcher;
        while (startManually) {
            for (Government government : GameMenuController.game.getGovernments()) {
                if (GameMenuController.checkAllGovernmentsDead()) {
                    GameMenuController.countScores();
                    return;
                }
                if (government.isDead()) continue;
                numberOfTurns++;
                Game.currentGovernment = government;
                output("Currently " + government.getUser().getUsername() + " is playing");
                while (startManually) {
                    command = input();
                    if(command.matches("\\s*show\\s+related\\s+commands\\s*")) {
                        output("end\ndone\nadd <username>\ndropbuilding -x <X> -y <Y> -t <type>\nclear -x <X> -y <Y>\n" +
                                "next turn\nselect building -x <X> -y <Y>\ndraw map -x <X> -y <Y>\nshow resources\n" +
                                "dropunit -x <X> -y <Y> -t <type> -c <count>\nselect unit -x <X> -y <Y> -t <type>\n" +
                                "cancel patrol -x <X> -y <Y>\nenter trade menu\nenter store menu");
                    }
                    else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.END)) != null) {
                        output("Game ended manually");
                        return;
                    } else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.NEXT_TURN)) != null) {
                        GameMenuController.nextTurn();
                        break;
                    } else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.CLEAR)) != null) {
                        int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
                        MapMenuController.clear(x, y);
                    } else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.DROP_BUILDING)) != null) {
                        int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
                        String type = Controller.removeDoubleQuote(matcher.group("type"));
                        BuildingMenuController.dropBuilding(x, y, type);
                    } else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.DROP_STAIRS)) != null) {
                        int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
                        BuildingMenuController.dropStairs(x, y);
                    } else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SELECT_BUILDING)) != null) {
                        int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
                        BuildingMenuController.selectBuilding(x, y);
                    } else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.DRAW_MAP)) != null) {
                        int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
                        MapMenu.movingMap(x, y);
                    } else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_RESOURCES)) != null) {
                        GameMenuController.showResources();
                    } else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.DROP_UNIT)) != null) {
                        int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
                        int count = Integer.parseInt(matcher.group("count"));
                        String type = Controller.removeDoubleQuote(matcher.group("type"));
                        UnitMenuController.dropUnit(x, y, count, type);
                    } else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SELECT_UNIT)) != null) {
                        int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
                        String type = Controller.removeDoubleQuote(matcher.group("type"));
                        UnitMenuController.selectUnit(x, y, type);
                    } else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.SHOW_DETAILS)) != null) {
                        int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
                        MapMenuController.showDetails(x, y);
                    } else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.CANCEL_PATROL)) != null) {
                        int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
                        UnitMenuController.cancelPatrol(x, y);
                    } else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.TRADE)) != null) {
                        output("Entered trade menu");
                        TradeMenu tradeMenu = new TradeMenu();
                        tradeMenu.run();
                        output("Exited trade menu");
                    } else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.STORE)) != null) {
                        output("Entered store menu");
                        StoreMenu storeMenu = new StoreMenu();
                        storeMenu.run();
                        output("Exited store menu");
                    } else {
                        output("Invalid command!");
                    }
                }
                if (!startManually) return;
            }
        }
    }

    private static void createMap() {
        output("Which map do you want? Type it's number!" +
                "\n1) 200 in 200 map" +
                "\n2) 400 in 400 map");
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

    private static void valueDefaults() {
        int k = GameMenuController.mapSize;
        defaultXPositions[0] = 0;
        defaultYPositions[0] = 0;
        defaultXPositions[1] = 2;
        defaultYPositions[1] = 0;
        defaultXPositions[2] = 0;
        defaultYPositions[2] = 2;
        defaultXPositions[3] = k - 1;
        defaultYPositions[3] = 0;
        defaultXPositions[4] = k - 3;
        defaultYPositions[4] = 0;
        defaultXPositions[5] = k - 1;
        defaultYPositions[5] = 2;
        defaultXPositions[6] = 0;
        defaultYPositions[6] = k - 1;
        defaultXPositions[7] = 0;
        defaultYPositions[7] = k - 3;
        defaultXPositions[8] = 2;
        defaultYPositions[8] = k - 1;
        defaultXPositions[9] = k - 1;
        defaultYPositions[9] = k - 1;
        defaultXPositions[10] = k - 3;
        defaultYPositions[10] = k - 1;
        defaultXPositions[11] = k - 1;
        defaultYPositions[11] = k - 3;
        defaultXPositions[12] = k / 2;
        defaultYPositions[12] = k / 2;
        defaultXPositions[13] = k / 2;
        defaultYPositions[13] = k / 2 - 2;
        defaultXPositions[14] = k / 2 - 2;
        defaultYPositions[14] = k / 2;
    }

    public static void addPopulation(Government government, int count) {
        government.setPopulation(count);
        Person person;
        for (int i = 0; i < count; i++) {
            person = new Person(null, PersonType.PEASANT, government.getUser());
            government.getPeople().add(person);
        }
    }

    private void selectUsers() {
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
            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.ADD_USER)) != null) {
                String username = matcher.group("username");
                User user = Controller.findUserByUsername(username);
                if (user == null) {
                    output("User " + username + " not found");
                    continue;
                }
                boolean checkFlag = false;
                for (Government addedGovernment : GameMenuController.game.getGovernments()) {
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
            } else {
                output("Invalid command!");
            }
        }
    }

    public static void giveDefaultBuildings(Government government, int counter) {
        BuildingType buildingType = Building.ALL_BUILDINGS.get("stockpile");
        Building building = Building.createBuildings("stockpile", defaultXPositions[3 * counter],
                defaultYPositions[3 * counter], buildingType, government.getUser());
        government.getBuildings().add(building);
        GameMenuController.game.getMap().getTiles()[defaultXPositions[3 * counter]][defaultYPositions[3 * counter]].
                setBuilding(building);
        buildingType = Building.ALL_BUILDINGS.get("campfire");
        building = Building.createBuildings("campfire", defaultXPositions[3 * counter + 1],
                defaultYPositions[3 * counter + 1], buildingType, government.getUser());
        government.getBuildings().add(building);
        GameMenuController.game.getMap().getTiles()[defaultXPositions[3 * counter + 1]][defaultYPositions[3 * counter + 1]].
                setBuilding(building);
        buildingType = Building.ALL_BUILDINGS.get("keep");
        building = Building.createBuildings("keep", defaultXPositions[3 * counter + 2],
                defaultYPositions[3 * counter + 2], buildingType, government.getUser());
        government.getBuildings().add(building);
        GameMenuController.game.getMap().getTiles()[defaultXPositions[3 * counter + 2]][defaultYPositions[3 * counter + 2]].
                setBuilding(building);

    }

    public static void giveDefaultResources(Government government, Storage storage) {
        Resource resource;
        resource = Resource.createResource(ResourceType.getResourceByName("wood"), 100);
        storage.addToStorage(resource);
        resource = Resource.createResource(ResourceType.getResourceByName("stone"), 100);
        storage.addToStorage(resource);
        resource = Resource.createResource(ResourceType.getResourceByName("iron"), 100);
        storage.addToStorage(resource);
        resource = Resource.createResource(ResourceType.getResourceByName("gold"), 1000);
        storage.addToStorage(resource);
    }
}
