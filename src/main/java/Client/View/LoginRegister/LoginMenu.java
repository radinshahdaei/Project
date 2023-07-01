package Client.View.LoginRegister;


import Client.View.Commands.LoginMenuCommands;
import Client.Controller.LoginMenuController;

import java.util.regex.Matcher;

import static Client.View.InputOutput.input;
import static Client.View.InputOutput.output;
import static Client.Controller.Controller.removeDoubleQuote;

public class LoginMenu {
    public boolean run() throws InterruptedException {
        String command;
        Matcher matcher;
        while (true) {
            command = input();
            if(command.matches("\\s*show\\s+related\\s+commands\\s*")) {
                output("back");
                output("user login -u <username> -p <password> --stay-logged-in");
                output("forgot my password");
            }
            else if ((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.BACK)) != null) {
                output("Entered Main Menu!");
                return false;
            } else if ((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.LOGIN)) != null) {
                String username = removeDoubleQuote(matcher.group("username"));
                String password = removeDoubleQuote(matcher.group("password"));
                String stayLoggedIn = removeDoubleQuote(matcher.group("stayLoggedIn"));
                if (LoginMenuController.login(username , password , stayLoggedIn)) return true;
            } else if ((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.FORGOT_PASSWORD)) != null) {
                LoginMenuController.forgotPassword();
            } else {
                output("Invalid command!");
            }
        }
    }
}