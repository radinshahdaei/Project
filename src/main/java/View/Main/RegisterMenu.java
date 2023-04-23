package View.Main;

import Controller.ManageData;
import Controller.RegisterMenuController;
import View.Commands.RegisterMenuCommands;

import java.util.regex.Matcher;

import static View.InputOutput.input;
import static View.InputOutput.output;
import static Controller.Controller.removeDoubleQuote;

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
                String username = removeDoubleQuote(matcher.group("username"));
                String nickname = removeDoubleQuote(matcher.group("nickname"));
                String password = removeDoubleQuote(matcher.group("password"));
                String passwordConfirmation = removeDoubleQuote(matcher.group("passwordConfirmation"));
                String passwordRandom = removeDoubleQuote(matcher.group("passwordRandom"));
                String email = removeDoubleQuote(matcher.group("email"));
                String slogan = removeDoubleQuote(matcher.group("slogan"));
                String containsSlogan = removeDoubleQuote(matcher.group("containsSlogan"));
                String sloganRandom = removeDoubleQuote(matcher.group("sloganRandom"));
                securityQuestion(RegisterMenuController.register(username , nickname , password , passwordConfirmation ,
                        passwordRandom , email , slogan , containsSlogan , sloganRandom));
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
                int questionNumber = Integer.parseInt(matcher.group("questionNumber"));
                String answer = removeDoubleQuote(matcher.group("answer"));
                String answerConfirmation = removeDoubleQuote(matcher.group("answerConfirmation"));
                if (RegisterMenuController.securityQuestion(questionNumber , answer , answerConfirmation)) break;
            } else {
                output("Invalid command!");
            }
        }
    }
}