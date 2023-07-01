package Controller;

import Client.Client;
import Model.Store;
import Model.User;
import View.LoginRegister.MainMenu;
import View.Start.StartMenu;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Controller {
    public static ArrayList<User> users = ManageData.loadUsers();
    public static User currentUser = ManageData.loadCurrentUser();
    public static boolean stayLoggedIn = currentUser != null;

    public static Stage stage;

    public static Client client;

    public static void setClient(Client client) {
        client = client;
    }

    static {
        if (users == null) users = new ArrayList<>();
        if (currentUser != null) {
            currentUser = findUserByUsername(currentUser.getUsername());
        }
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        Controller.stage = stage;
    }

    public static void run() throws InterruptedException {
        Store.initializeCommodities();
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
        ManageData.saveUsers();
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static void stayLoggedIn() {
        stayLoggedIn = true;
    }

    public static String removeDoubleQuote(String str) {
        if (str == null) return null;
        if (str.charAt(0) == '\"' && str.charAt(str.length() - 1) == '\"') {
            return str.substring(1, str.length() - 1);
        }
        return str;
    }
}