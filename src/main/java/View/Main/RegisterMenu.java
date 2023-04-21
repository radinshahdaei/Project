package View.Main;

import Controller.ManageData;
import Controller.RegisterMenuController;
import View.Commands.RegisterMenuCommands;

import java.util.regex.Matcher;

import static View.InputOutput.input;
import static View.InputOutput.output;

public class RegisterMenu {
    public void run() {
        String command;
        Matcher matcher;
        while (true) {
            command = input();
            if ((matcher = RegisterMenuCommands.getMatcher(command, RegisterMenuCommands.BACK)) != null) {
                output("Entered Main Menu!");
                break;
            } else if ((matcher = RegisterMenuCommands.getMatcher(command, RegisterMenuCommands.REGISTER)) != null) {
                securityQuestion(RegisterMenuController.register(matcher));
            } else {
                output("Invalid command!");
            }
        }
    }

    public void securityQuestion(Boolean result) {
        if (!result) return;
        output("Pick your security question:\n" +
                "1. What is my father’s name?\n" +
                "2. What was my first pet’s name?\n" +
                "3. What is my mother’s last name?\n" +
                "type \"back\" to register again.");
        String command;
        Matcher matcher;
        while (true) {
            command = input();
            if ((matcher = RegisterMenuCommands.getMatcher(command, RegisterMenuCommands.BACK)) != null) {
                break;
            } else if ((matcher = RegisterMenuCommands.getMatcher(command, RegisterMenuCommands.SECURITY_QUESTION)) != null) {
                if (RegisterMenuController.securityQuestion(matcher)) break;
            } else {
                output("Invalid command!");
            }
        }
    }
}