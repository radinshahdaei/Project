package Server.View.Start;

import Client.View.Commands.StartMenuCommands;
import Client.Controller.StartMenuController;
import Client.View.Game.GameMenu;
import Client.View.Start.ProfileMenu;

import java.util.regex.Matcher;

import static Client.View.InputOutput.input;
import static Client.View.InputOutput.output;

public class StartMenu {
    public String run() {
        String command;
        Matcher matcher;
        output("[GAME][PROFILE]");
        while (true) {
            command = input();
            if(command.matches("\\s*show\\s+related\\s+commands\\s*")) {
                output("logout");
                output("exit");
                output("enter profile menu");
                output("start game");
            }
            else if ((matcher = StartMenuCommands.getMatcher(command, StartMenuCommands.LOGOUT)) != null) {
                StartMenuController.logout();
                output("Logged out");
                return "logged out";
            }
            else if ((matcher = StartMenuCommands.getMatcher(command, StartMenuCommands.EXIT)) != null) {
                StartMenuController.exit();
                output("Exited");
                return "exit";
            }
            else if ((matcher = StartMenuCommands.getMatcher(command, StartMenuCommands.ENTER_PROFILE_MENU)) != null) {
                ProfileMenu profileMenu = new ProfileMenu();
                output("Entered Profile menu");
                profileMenu.run();
            }
            else if ((matcher = StartMenuCommands.getMatcher(command, StartMenuCommands.START_GAME)) != null) {
                GameMenu gameMenu = new GameMenu();
                output("Game started.\nYou have entered the game menu.");
                gameMenu.run();
            }
            else {
                output("Invalid command");
            }
        }
    }
}
