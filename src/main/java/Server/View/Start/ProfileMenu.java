package Server.View.Start;

import Client.View.Commands.ProfileMenuCommands;
import Client.Controller.ProfileMenuController;

import static Client.View.InputOutput.input;
import static Client.View.InputOutput.output;
import static Client.Controller.Controller.removeDoubleQuote;

import java.util.regex.Matcher;

public class ProfileMenu {
    public void run() {
        String command;
        Matcher matcher;
        while (true) {
            command = input();
            if(command.matches("\\s*show\\s+related\\s+commands\\s*")) {
                output("back");
                output("exit");
                output("profile change -u <username>");
                output("profile change -n <nickname>");
                output("profile change password -o <oldpassword> -n <newpassword>");
                output("profile change email -e <email>");
                output("profile change solgan -s <slogan>");
                output("profile remove slogan");
                output("profile display highscore");
                output("profile display slogan");
                output("profile display rank");
                output("profile display");
            }
            else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.BACK)) != null) {
                output("Entered Start Menu");
                return;
            }
            else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.EXIT)) != null) {
                ProfileMenuController.exit();
                output("Exited");
                System.exit(0);
            }
            else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_USERNAME)) != null) {
                String newUsername = removeDoubleQuote(matcher.group("username"));
                ProfileMenuController.changeUserName(newUsername);
            }
            else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_NICKNAME)) != null) {
                String newNickname = removeDoubleQuote(matcher.group("nickname"));
                ProfileMenuController.changeNickname(newNickname);
            }
            else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_PASSWORD)) != null) {
                String oldPassword = removeDoubleQuote(matcher.group("oldPassword"));
                String newPassword = removeDoubleQuote(matcher.group("newPassword"));
                ProfileMenuController.changePassword(oldPassword , newPassword);
            }
            else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_EMAIL)) != null) {
                String newEmail = removeDoubleQuote(matcher.group("email"));
                ProfileMenuController.changeEmail(newEmail);
            }
            else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_SLOGAN)) != null) {
                String newSlogan = removeDoubleQuote(matcher.group("slogan"));
                ProfileMenuController.changeSlogan(newSlogan);
            }
            else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.REMOVE_SLOGAN)) != null) {
                ProfileMenuController.removeSlogan();
            }
            else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_HIGHSCORE)) != null) {
                ProfileMenuController.displayHighScore();
            }
            else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_SLOGAN)) != null) {
                ProfileMenuController.displaySlogan();
            }
            else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_RANK)) != null) {
                ProfileMenuController.displayRank();
            }
            else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY)) != null) {
                ProfileMenuController.display();
            }
            else {
                output("Invalid command");
            }
        }
    }
}
