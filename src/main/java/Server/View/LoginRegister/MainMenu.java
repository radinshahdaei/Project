package Server.View.LoginRegister;

import Client.View.Commands.MainMenuCommands;
import Client.Controller.Controller;
import Client.Controller.ManageData;
import Client.View.LoginRegister.LoginMenu;
import Client.View.LoginRegister.RegisterMenu;

import java.util.regex.Matcher;

import static Client.View.InputOutput.input;
import static Client.View.InputOutput.output;

public class MainMenu {
    public String run() throws InterruptedException {
        output("[REGISTER][LOGIN]");
        String command;
        Matcher matcher;
        while (true) {
            command = input();
            if(command.matches("\\s*show\\s+related\\s+commands\\s*")) {
                output("exit");
                output("enter register menu");
                output("enter loggin menu");
            }
            else if ((matcher = MainMenuCommands.getMatcher(command, MainMenuCommands.EXIT)) != null) {
                output("Exited!");
                if (!Controller.stayLoggedIn) Controller.setCurrentUser(null);
                ManageData.saveCurrentUser();
                ManageData.saveUsers();
                return "exit";
            } else if ((matcher = MainMenuCommands.getMatcher(command, MainMenuCommands.REGISTER_MENU)) != null) {
                output("Entered Register Menu!");
                RegisterMenu registerMenu = new RegisterMenu();
                registerMenu.run();
            } else if ((matcher = MainMenuCommands.getMatcher(command, MainMenuCommands.LOGIN_MENU)) != null) {
                output("Entered Login Menu!");
                LoginMenu loginMenu = new LoginMenu();
                if (loginMenu.run()) return "logged in";
            } else {
                output("Invalid command!");
            }
        }
    }
}