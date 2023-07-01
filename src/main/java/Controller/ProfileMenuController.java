package Controller;

import Model.User;
import View.InputOutput;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static View.InputOutput.output;

public class ProfileMenuController {
    private static String finalUsername;
    private static String finalPassword;
    private static String finalEmail;
    public static void changeUserName(String newUsername) {
        if (!checkUsername(newUsername)) return;
        Controller.currentUser.setUsername(finalUsername);
        InputOutput.output("New username set!");
    }
    private static boolean checkUsername(String username) {
        if (!checkUsernameFormat(username)) {
            InputOutput.output("Invalid username format");
            return false;
        }

        if (Controller.findUserByUsername(username) != null) {
            String newUsername = newUsername(username);
            InputOutput.output("Username already exists, you can use " + newUsername + " instead!\n" +
                    "chose an option: 1) confirm    2) decline");
            String input = InputOutput.input();
            if (input.equals("1")) {
                InputOutput.output("Confirmed!");
                username = newUsername;
            } else {
                InputOutput.output("Declined!");
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


    public static void changeNickname(String newNickname) {
        Controller.currentUser.setNickname(newNickname);
        InputOutput.output("New nickname set!");
    }


    public static void changePassword(String oldPassword , String newPassword) {
        String passwordConfirmation;
        InputOutput.output("Please enter your new password again");
        passwordConfirmation = InputOutput.input();
        if (!ManageData.decrypt(Controller.currentUser.getPassword(), oldPassword)) {
            InputOutput.output("Current Password in incorrect!");
            return;
        }
        if (ManageData.encrypt(newPassword).equals(Controller.currentUser.getPassword())) {
            InputOutput.output("Please enter a new Password");
            return;
        }
        if (checkPassword(newPassword, passwordConfirmation)) {
            RegisterMenuController.captcha();
            Controller.currentUser.setPassword(ManageData.encrypt(finalPassword));
            InputOutput.output("New Password set!");
        }

    }
    public static boolean checkPassword(String password, String passwordConfirmation) {
        String passwordStrength = checkPasswordStrength(password);
        if (!passwordStrength.equals("success")) {
            InputOutput.output(passwordStrength);
            return false;
        }
        if (!password.equals(passwordConfirmation)) {
            InputOutput.output("Password and it's confirmation do not match!");
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


    public static void changeEmail(String newEmail) {
        if (checkEmail(newEmail)) {
            Controller.currentUser.setEmail(finalEmail);
            InputOutput.output("New email set!");
        }
    }
    private static boolean checkEmail(String email) {
        if (Controller.findUserByEmail(email) != null) {
            InputOutput.output("A user with this email already exists!");
            return false;
        }
        if (!checkEmailFormat(email)) {
            InputOutput.output("Invalid email!");
            return false;
        }
        finalEmail = email;
        return true;
    }
    private static boolean checkEmailFormat(String email) {
        Matcher matcher = Pattern.compile("[\\w._]+@[\\w._]+\\.[\\w._]{2,}").matcher(email);
        return matcher.matches();
    }

    public static void changeSlogan(String newSlogan) {
        Controller.currentUser.setSlogan(newSlogan);
        InputOutput.output("New Slogan set");
    }
    public static void removeSlogan() {
        Controller.currentUser.setSlogan(null);
        InputOutput.output("Slogan removed");
    }

    public static void displayHighScore() {
        int highscore = Controller.currentUser.getHighScore();
        InputOutput.output(String.valueOf(highscore));
    }

    public static void displaySlogan() {
        if (Controller.currentUser.getSlogan() == null) InputOutput.output("Slogan is empty");
        InputOutput.output(Controller.currentUser.getSlogan());
    }

    public static ArrayList<User> sortUsers() {
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
                InputOutput.output(String.valueOf(index));
            }
            index++;
        }
        InputOutput.output("There was an error! ");
    }

    public static void display() {
        InputOutput.output("username: " + Controller.currentUser.getUsername());
        InputOutput.output("nickname: " + Controller.currentUser.getNickname());
        if (Controller.currentUser.getSlogan() != null) InputOutput.output("slogan: " + Controller.currentUser.getSlogan());
        InputOutput.output("email: " + Controller.currentUser.getEmail());
        InputOutput.output("highscore: " + String.valueOf(Controller.currentUser.getHighScore()));
    }
    public static void exit() {
        if (!Controller.stayLoggedIn) Controller.currentUser = null;
        ManageData.saveUsers();
        ManageData.saveCurrentUser();
        return;
    }
}
