package Client.Controller;

import Client.Model.User;
import Client.View.InputOutput;

import java.io.IOException;

import static Client.Controller.RegisterMenuController.captcha;
import static Client.Controller.RegisterMenuController.checkPassword;
import static Client.View.InputOutput.output;

public class LoginMenuController {
    static int timer = 1;

    public static boolean login(String username , String password , String stayLoggedIn) throws InterruptedException {
        if (!checkEmptyField(username, password)) return false;
        if (!checkUserPass(username, password, stayLoggedIn)) return false;
        return true;
    }

    public static void changePassword(User user) {
        String password;
        String passwordConfirmation;
        InputOutput.output("Enter your new password:");
        password = InputOutput.input();
        InputOutput.output("Enter password confirmation:");
        passwordConfirmation = InputOutput.input();
        if (checkPassword(password, passwordConfirmation, null)) {
            captcha();
            user.setPassword(ManageData.encrypt(password));
            InputOutput.output("New Password set!");
        }

    }

    public static void forgotPassword() {
        InputOutput.output("Enter your username:");
        String username;
        String answer;
        username = InputOutput.input();
        if (Controller.findUserByUsername(username) == null) {
            InputOutput.output("No user with this username found!");
            return;
        }
        User user = Controller.findUserByUsername(username);
        int questionNumber = user.getQuestionNumber();
        if (questionNumber == 1) {
            InputOutput.output("What is my father’s name?");
        } else if (questionNumber == 2) {
            InputOutput.output("What was my first pet’s name?");
        } else if (questionNumber == 3) {
            InputOutput.output("What is my mother’s last name?");
        }
        if (user.getAnswer().equals(InputOutput.input())) changePassword(user);
        else InputOutput.output("Wrong answer!");
    }

    public static boolean checkEmptyField(String username, String password) {
        if (username == null || password == null) {
            InputOutput.output("Empty Field!");
            return false;
        }
        return true;
    }

    public static boolean checkUserPass(String username, String password, String stayLoggedIn) throws InterruptedException {
        User user = Controller.findUserByUsername(username);
        if (user == null) {
            InputOutput.output("Username does not exists");
            return false;
        } else if (!ManageData.decrypt(user.getPassword(), password)) {
            InputOutput.output("Username and password didn't match!");
            waitForLogin();
            return false;
        }
        captcha();
        if (stayLoggedIn != null) Controller.stayLoggedIn();
        timer = 1;
        Controller.setCurrentUser(user);
        InputOutput.output("User logged in successfully!");
        return true;
    }

    public static String checkUsername(String username){
        User user = Controller.findUserByUsername(username);
        if (user == null) {
            return "Username doesn't exist!";
        } return "Success";
    }

    public static String checkUsernameAndPassword(String username,String password){
        User user = Controller.findUserByUsername(username);
        if (user != null && !ManageData.decrypt(user.getPassword(), password)) {
            return "Username and password don't match";
        } return "Success";
    }

    public static void waitForLogin() throws InterruptedException {
        timer++;
        InputOutput.output("You cannot login for " + timer * 5 + " seconds!");
        Thread.sleep(5000L * timer);
    }

    public static int getTimer() {
        return timer;
    }

    public static void connectToServer() throws IOException {
//        Socket socket = new Socket("local host",8001);
//        Controller.setSocket(socket);
    }
}