
package View.Game;

import Controller.Controller;
import Controller.GameMenuController;
import Controller.MapMenuController;
import View.Commands.GameMenuCommands;
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
            if (command.matches("\\s*show\\s+related\\s+commands\\s*")) {
                output("end map\nsettexture -x <X> -y <Y> -t <type>\nsettexture -x1 <X1> -x2 <X2> -y1 <Y1> -y2 <Y2> -t <type>\nclear -x <X> -y <Y>\ndraw map\nend draw map\nmap move up <amountUp> down <amountDown> left <amountLeft> right <amountRight>\nshow details -x <X> -y <Y>\ndropbuilding -x <X> -y <Y> -t <type>");
            } else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.END_MAP)) != null) {
                output("Map making ended!");
                return;
            } else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.SET_TEXTURE)) != null) {
                String type = matcher.group("type");
                int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
                MapMenuController.setTexture(type, x, y);
            } else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.SET_TEXTURE_REC)) != null) {
                String type = matcher.group("type");
                int x1 = Integer.parseInt(matcher.group("X1")), x2 = Integer.parseInt(matcher.group("X2")),
                        y1 = Integer.parseInt(matcher.group("Y1")), y2 = Integer.parseInt(matcher.group("Y2"));
                MapMenuController.setTextureRectangle(type, x1, x2, y1, y2);
            } else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.CLEAR)) != null) {
                int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
                MapMenuController.clear(x, y);
            } else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.DRAW)) != null) {
                movingMap(0, 0);
            } else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.SHOW_DETAILS)) != null) {
                int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
                MapMenuController.showDetails(x, y);
            } else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.DROP_ROCK)) != null) {
                int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
                char direction = matcher.group("direction").charAt(0);
                MapMenuController.dropRock(x, y, direction);
            } else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.DROP_TREE)) != null) {
                int x = Integer.parseInt(matcher.group("X")), y = Integer.parseInt(matcher.group("Y"));
                String type = matcher.group("type");
                MapMenuController.dropTree(x, y, type);
            } else {
                output("Invalid command!");
            }
        }
    }

    public static void movingMap(int x, int y) {
        Matcher matcher;
        String command;
        int topX = x, topY = y;
        int lastTopX = x, lastTopY = y;
        boolean checkInMap = true;
        while (true) {
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
                if (matcher.group("up") != null) topY -= amountUp;
                if (matcher.group("down") != null) topY += amountDown;
                if (matcher.group("left") != null) topX -= amountLeft;
                if (matcher.group("right") != null) topX += amountRight;
                checkInMap = (topX >= 0 && topX <= GameMenuController.game.getMap().getMapSize() - MapMenuController.SIZEX) &&
                        (topY >= 0 && topY <= GameMenuController.game.getMap().getMapSize() - MapMenuController.SIZEY);
            } else if (MapMenuCommands.getMatcher(command, MapMenuCommands.END_SHOW_MAP) != null) {
                output("Stopped drawing map");
                break;
            } else {
                output("Invalid command!");
            }
        }
    }
}