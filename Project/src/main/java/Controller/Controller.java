package Controller;

import Model.User;
import View.Main.MainMenu;
import View.Start.StartMenu;

import java.util.ArrayList;


public class Controller {
    public static ArrayList<User> users = ManageData.loadUsers();
    public static User currentUser = ManageData.loadCurrentUser();
    public static boolean stayLoggedIn = currentUser != null;

    public static void run() throws InterruptedException {
        while (true) {
            if (currentUser == null) {
                MainMenu mainMenu = new MainMenu();
                if (mainMenu.run().equals("exit")) break;
            }
            StartMenu startMenu = new StartMenu();
            if (startMenu.run().equals("exit")) break;
        }
    }

    public static User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

    public static User findUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) return user;
        }
        return null;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static void stayLoggedIn() {
        stayLoggedIn = true;
    }
}