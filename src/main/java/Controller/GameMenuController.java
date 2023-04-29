package Controller;

import Model.Game;
import Model.Resources.Resource;

import static View.InputOutput.output;
public class GameMenuController {
    public static Game game;
    public static int mapSize;
    public static void nextTurn() {

    }
    public static void showResources() {
        for (Resource resource:Game.currentGovernment.getResources()) {
            output(resource.getResourceType().name + ": " + resource.getCount());
        }
    }
}
