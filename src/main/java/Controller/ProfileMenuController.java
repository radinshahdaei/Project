package Controller;

import Model.User;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static Controller.RegisterMenuController.captcha;
import static View.InputOutput.input;
import static View.InputOutput.output;

public class ProfileMenuController {
    private static String finalUsername;
    private static String finalPassword;
    private static String finalEmail;
    public static void changeUserName(Matcher matcher) {
        String newUsername = matcher.group("username");
        if (!checkUsername(newUsername)) return;
        Controller.currentUser.setUsername(finalUsername);
        output("New username set!");
    }
    private static boolean checkUsername(String username) {
        if (!checkUsernameFormat(username)) {
            output("Invalid username format");
            return false;
        }

        if (Controller.findUserByUsername(username) != null) {
            String newUsername = newUsername(username);
            output("Username already exists, you can use " + newUsername + " instead!\n" +
                    "chose an option: 1) confirm    2) decline");
            String input = input();
            if (input.equals("1")) {
                output("Confirmed!");
                username = newUsername;
            } else {
                output("Declined!");
                return false;
            }
        }
        finalUsername = username;
        return true;
    }
    private static boolean checkUsernameFormat(String username) {
        Matcher matcher = Pattern.compile("[a-zA-Z0-9_]+").matcher(username);
        return matcher.matches();
    }
    private static String newUsername(String username) {
        double buffer;
        int number;
        String newUsername = username;
        while (true) {
            buffer = Math.random() * 10;
            number = (int) buffer;
            newUsername += String.valueOf(number);
            if (Controller.findUserByUsername(newUsername) == null) break;
        }
        return newUsername;
    }


    public static void changeNickname(Matcher matcher) {
        String newNickname = matcher.group("nickname");
        Controller.currentUser.setNickname(newNickname);
        output("New nickname set!");
    }


    public static void changePassword(Matcher matcher) {
        String oldPassword = matcher.group("oldPassword"), newPassword = matcher.group("newPassword");
        String passwordConfirmation;
        output("Please enter your new password again");
        passwordConfirmation = input();
        if (!ManageData.decrypt(Controller.currentUser.getPassword(), oldPassword)) {
            output("Current Password in incorrect!");
            return;
        }
        if (ManageData.encrypt(newPassword).equals(Controller.currentUser.getPassword())) {
            output("Please enter a new Password");
            return;
        }
        if (checkPassword(newPassword, passwordConfirmation)) {
            captcha();
            Controller.currentUser.setPassword(ManageData.encrypt(finalPassword));
            output("New Password set!");
        }

    }
    public static boolean checkPassword(String password, String passwordConfirmation) {
        String passwordStrength = checkPasswordStrength(password);
        if (!passwordStrength.equals("success")) {
            output(passwordStrength);
            return false;
        }
        if (!password.equals(passwordConfirmation)) {
            output("Password and it's confirmation do not match!");
            return false;
        }
        finalPassword = password;
        return true;
    }
    public static String checkPasswordStrength(String password) {
        String strength = checkStrength(password);
        if (!strength.equals("")) return ("weak password, errors: " + strength + "!");
        return "success";
    }
    public static String checkStrength(String password) {
        String output = "";
        if (password.length() < 6) output += "[short length]";
        if (!password.matches(".*[a-z].*")) output += "[no lowercase letters]";
        if (!password.matches(".*[A-Z].*")) output += "[no uppercase letters]";
        if (!password.matches(".*[0-9].*")) output += "[no numbers]";
        if (!password.matches(".*[?!@#$%^&*].*")) output += "[no special characters]";
        return output;
    }


    public static void changeEmail(Matcher matcher) {
        String newEmail = matcher.group("email");
        if (checkEmail(newEmail)) {
            Controller.currentUser.setEmail(finalEmail);
            output("New email set!");
        }
    }
    private static boolean checkEmail(String email) {
        if (Controller.findUserByEmail(email) != null) {
            output("A user with this email already exists!");
            return false;
        }
        if (!checkEmailFormat(email)) {
            output("Invalid email!");
            return false;
        }
        finalEmail = email;
        return true;
    }
    private static boolean checkEmailFormat(String email) {
        Matcher matcher = Pattern.compile("[\\w._]+@[\\w._]+\\.[\\w._]{2,}").matcher(email);
        return matcher.matches();
    }

    public static void changeSlogan(Matcher matcher) {
        String newSlogan = matcher.group("slogan");
        Controller.currentUser.setSlogan(newSlogan);
        output("New Slogan set");
    }
    public static void removeSlogan() {
        Controller.currentUser.setSlogan(null);
        output("Slogan removed");
    }

    public static void displayHighScore() {
        int highscore = Controller.currentUser.getHighScore();
        output(String.valueOf(highscore));
    }

    public static void displaySlogan() {
        if (Controller.currentUser.getSlogan() == null) output("Slogan is empty");
        output(Controller.currentUser.getSlogan());
    }

    private static ArrayList<User> sortUsers() {
        ArrayList<User> newUsers = new ArrayList<>();
        boolean flag = false;
        for (int i = 0 ; i < Controller.users.size() ; i++) {
            flag = false;
            for (int j = 0 ; j < newUsers.size() ; j++) {
                if (Controller.users.get(i).getHighScore() > newUsers.get(j).getHighScore()) {
                    newUsers.add(j, Controller.users.get(i));
                    flag = true;
                    break;
                }
                if (Controller.users.get(i).getHighScore() == newUsers.get(j).getHighScore() &&
                        Controller.users.get(i).getUsername().compareTo(newUsers.get(j).getUsername()) < 0) {
                    newUsers.add(j, Controller.users.get(i));
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                newUsers.add(Controller.users.get(i));
            }
        }
        return newUsers;
    }

    public static void displayRank() {
        int index = 1;
        ArrayList<User> sorted = sortUsers();
        for (User user : sorted) {
            if (user.equals(Controller.currentUser)) {
                output(String.valueOf(index));
            }
            index++;
        }
        output("There was an error! ");
    }

    public static void display() {
        output("username: " + Controller.currentUser.getUsername());
        output("nickname: " + Controller.currentUser.getNickname());
        if (Controller.currentUser.getSlogan() != null) output("slogan: " + Controller.currentUser.getSlogan());
        output("email: " + Controller.currentUser.getEmail());
        output("highscore: " + String.valueOf(Controller.currentUser.getHighScore()));
    }
    public static void exit() {
        if (!Controller.stayLoggedIn) Controller.currentUser = null;
        ManageData.saveUsers();
        ManageData.saveCurrentUser();
        return;
    }
}
