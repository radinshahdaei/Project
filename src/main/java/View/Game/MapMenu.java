package View.Game;

import Controller.GameMenuController;
import Controller.MapMenuController;
import View.Commands.MapMenuCommands;

import java.util.regex.Matcher;

import static View.InputOutput.input;
import static View.InputOutput.output;

public class MapMenu {
    public void run() {
        String command;
        Matcher matcher;
        while (true) {
            command = input();
            if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.END_MAP)) != null) {
                output("Map making ended!");
                return;
            }
            else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.SET_TEXTURE)) != null) {
                MapMenuController.setTexture(matcher);
            }
            else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.SET_TEXTURE_REC)) != null) {
                MapMenuController.setTextureRectangle(matcher);
            }
            else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.CLEAR)) != null) {
                MapMenuController.clear(matcher);
            }
            else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.DRAW)) != null) {
                movingMap();
            }
            else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.SHOW_DETAILS)) != null) {
                MapMenuController.showDetails(matcher);
            }
            else {
                output("Invalid command!");
            }
        }
    }

    public static void movingMap() {
        Matcher matcher;
        String command;
        int topX = 0, topY = 0;
        int lastTopX = 0, lastTopY = 0;
        boolean checkInMap = true;
        do {
            if (checkInMap) {
                MapMenuController.DrawMap(topX, topY);
                lastTopY = topY;
                lastTopX = topX;
            } else {
                output("Out of the map borders!");
                topX = lastTopX;
                topY = lastTopY;
            }
            command = input();
            if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.MAP_MOVE)) != null) {
                String up = matcher.group("amountUp");
                String down = matcher.group("amountDown");
                String left = matcher.group("amountLeft");
                String right = matcher.group("amountRight");
                int amountUp = (up != null) ? Integer.parseInt(up) : 1;
                int amountDown = (down != null) ? Integer.parseInt(down) : 1;
                int amountLeft = (left != null) ? Integer.parseInt(left) : 1;
                int amountRight = (right != null) ? Integer.parseInt(right) : 1;
                if (matcher.group("up") != null) topY+=amountUp;
                if (matcher.group("down") != null) topY-=amountDown;
                if (matcher.group("left") != null) topX+=amountLeft;
                if (matcher.group("right") != null) topX-=amountRight;
                checkInMap = (topX >= 0 && topX <= GameMenuController.game.getMap().getMapSize() - MapMenuController.SIZEX) &&
                        (topY >= 0 && topY <= GameMenuController.game.getMap().getMapSize() - MapMenuController.SIZEY);
            }
        } while (MapMenuCommands.getMatcher(command, MapMenuCommands.END_SHOW_MAP) == null);
    }
}
