package Controller;

import Model.Building.*;
import Model.Game;
import Model.Government;
import Model.Resources.Resource;
import Model.Tile;

import java.util.HashMap;
import java.util.regex.Matcher;

import static View.InputOutput.output;

public class MapMenuController {
    public static final int SIZEX = 8;
    public static final int SIZEY = 3;
    public static void initializeMap(int mapSize) {
        Tile[][] tiles = new Tile[mapSize][mapSize];
        for (int i = 0; i < mapSize ; i++) {
            for (int j = 0 ; j < mapSize ; j++) {
                tiles[i][j] = new Tile();
                tiles[i][j].setTexture("Earth");
                tiles[i][j].setX(i);
                tiles[i][j].setY(j);
            }
        }
        GameMenuController.game.getMap().setTiles(tiles);
        GameMenuController.game.getMap().setMapSize(mapSize);
    }
    public static void setTexture(String type, int x ,int y) {
        GameMenuController.game.getMap().getTiles()[x][y].setTexture(type);
    }
    public static void setTextureRectangle(String type , int x1 , int x2 , int y1 , int y2) {
        for (int i = x1 ; i <= x2 ; i++ ){
            for (int j = y1 ; j <= y2 ; j++) {
                GameMenuController.game.getMap().getTiles()[i][j].setTexture(type);
            }
        }
    }
    public static void clear(int x , int y) {
        GameMenuController.game.getMap().getTiles()[x][y].setTexture("Earth");
        GameMenuController.game.getMap().getTiles()[x][y].getPeople().clear();
        GameMenuController.game.getMap().getTiles()[x][y].setBuilding(null);
    }
    public static void DrawMap(int topX, int topY) {
        HashMap<String, String> COLORS = changeColor();
        output(new String(new char[50]).replace("\0", "\r\n"));
        drawSeparators(SIZEX * 6);
        for (int y = 0 ; y < SIZEY ; y++) {
            output("|", 1);
            for (int x = 0 ; x < SIZEX ; x++) {
                int mapX = topX + x;
                int mapY = topY + y;
                drawAnEmptyLine(COLORS.get(GameMenuController.game.getMap().getTiles()[mapX][mapY].getTexture()),
                        COLORS.get("Reset"));
            }
            output("\n|", 1);
            for (int x = 0 ; x < SIZEX ; x++) {
                int mapX = topX + x;
                int mapY = topY + y;
                Tile tile = GameMenuController.game.getMap().getTiles()[mapX][mapY];
                String c;
                if (tile.getBuilding() != null) c = "##B##";
                else if (tile.getPeople().size() > 0) c = "##S##";
                else c = "#####";
                c = COLORS.get(GameMenuController.game.getMap().getTiles()[mapX][mapY].getTexture()) + c +
                        COLORS.get("Reset") + "|";
                output(c, 1);
            }
            output("\n|", 1);
            for (int x = 0 ; x < SIZEX ; x++) {
                int mapX = topX + x;
                int mapY = topY + y;
                drawAnEmptyLine(COLORS.get(GameMenuController.game.getMap().getTiles()[mapX][mapY].getTexture()),
                        COLORS.get("Reset"));
            }
            output("");
            drawSeparators(SIZEX * 6);
        }

    }
    public static HashMap<String, String> changeColor() {
        HashMap<String, String> map = new HashMap<>();
//        Black
        map.put("Stone", "\u001B[40m");
        map.put("Iron", "\u001B[40m");
//        Red
        map.put("Blood", "\u001B[41m");
//        Green
        map.put("Earth", "\u001B[42m");
        map.put("Grass", "\u001B[42m");
//        Blue
        map.put("Lake", "\u001B[44m");
//        Yellow
        map.put("Sand", "\u001B[43m");
//        Reset
        map.put("Reset", "\u001B[0m");
        return map;
    }
    private static void drawAnEmptyLine(String color, String reset) {
        output(color + "#####" + reset + "|", 1);
    }
    private static void drawSeparators(int times) {
        for (int i = 0 ; i < times ; i++) output("-", 1);
        output("");
    }
    public static void showDetails(int x , int y) {
        Tile tile = GameMenuController.game.getMap().getTiles()[x][y];
        output("x: " + tile.getX() + " , y: " + tile.getY());
        output("texture: " + tile.getTexture());
        if (tile.getBuilding() != null) output("Building: " + tile.getBuilding().getName());
        else if (tile.getPeople().size() > 0) output("People: " + tile.getPeople().get(0).getName());
    }

    public static void dropBuilding(int x, int y, String type) {
        if (checkSimpleErrors(x, y)) return;
        BuildingType buildingType = Building.ALL_BUILDINGS.get(type);
        if (buildingType == null) {
            output("This building does not exists!");
            return;
        }
        Building building = Building.createBuildings(type, x, y, buildingType);
        if (checkIfEnoughResourcesExist(building)) return;
        reduceRecommendedResources(building);
        Game.currentGovernment.getBuildings().add(building);
        GameMenuController.game.getMap().getTiles()[x][y].setBuilding(building);
        output("building successfully made");
    }

    private static void reduceRecommendedResources(Building building) {
        for (Resource resource: building.getPrice()) {
            for (Resource governmentResource:Game.currentGovernment.getResources()) {
                if (resource.getResourceType().name.equals(governmentResource.getResourceType().name)) {
                    governmentResource.setCount(governmentResource.getCount() - resource.getCount());
                }
            }
        }
    }

    private static boolean checkIfEnoughResourcesExist(Building building) {
        for (Resource resource: building.getPrice()) {
            boolean flag = false;
            for (Resource governmentResource:Game.currentGovernment.getResources()) {
                if (resource.getResourceType().name.equals(governmentResource.getResourceType().name)) {
                    flag = true;
                    if (resource.getCount() > governmentResource.getCount()) {
                        output("Not enough resources to buy this building");
                        return true;
                    }
                }
            }
            if (!flag) {
                output("Not enough resources to buy this building");
                return true;
            }
        }
        return false;
    }

    private static boolean checkSimpleErrors(int x, int y) {
        if (x >= GameMenuController.mapSize || y >= GameMenuController.mapSize) {
            output("Invalid coordinates");
            return true;
        }
        if (GameMenuController.game.getMap().getTiles()[x][y].getBuilding() != null) {
            output("A building already exists on this tile");
            return true;
        }
        if (GameMenuController.game.getMap().getTiles()[x][y].getPeople().size() > 0) {
            output("You cannot build on top of people!");
            return true;
        }
        return false;
    }
}
