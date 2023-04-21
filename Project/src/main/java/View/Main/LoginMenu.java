package View.Main;

import Controller.Controller;
import Controller.LoginMenuController;
import View.Commands.LoginMenuCommands;

import java.util.regex.Matcher;

import static View.InputOutput.input;
import static View.InputOutput.output;

public class LoginMenu {
    public boolean run() throws InterruptedException {
        String command;
        Matcher matcher;
        while (true) {
            command = input();
            if ((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.BACK)) != null) {
                output("Entered Main Menu!");
                return false;
            } else if ((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.LOGIN)) != null) {
                if (LoginMenuController.login(matcher)) return true;
            } else if ((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.FORGOT_PASSWORD)) != null) {
                LoginMenuController.forgotPassword();
            } else {
                output("Invalid command!");
            }
        }
    }
}